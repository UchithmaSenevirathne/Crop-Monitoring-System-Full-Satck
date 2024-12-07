// login.js
document.addEventListener("DOMContentLoaded", () => {
    const loginForm = document.querySelector("form");
    
    loginForm.addEventListener("submit", async (e) => {
      e.preventDefault(); // Prevent the form from refreshing the page
      
      const email = document.getElementById("email").value;
      const password = document.getElementById("password").value;
  
      const loginPayload = { email, password };
  
      try {
        const response = await fetch("http://localhost:8080/backend/user/authenticate", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(loginPayload),
        });
  
        if (!response.ok) {
          if (response.status === 401) {
            alert("Invalid credentials. Please try again.");
          } else {
            alert("Login failed. Please try again later.");
          }
          return;
        }
  
        const data = await response.json();
        const { code, message, data: authData } = data;

        console.log(code)
  
        if (code === 201) {
          alert(message);
          // Save token and user details (optional)
          localStorage.setItem("token", authData.token);
          localStorage.setItem("role", authData.role);
          localStorage.setItem("email", authData.email);
  
          // Redirect user based on role
          if (authData.role === "MANAGER") {
            window.location.href = "./dashboard.html";
          } else if (authData.role === "ADMINISTRATIVE") {
            window.location.href = "./dashboard.html";
          } else if (authData.role === "SCIENTIST") {
            window.location.href = "./dashboard.html";
          }
        } else {
          alert("Unexpected error: " + message);
        }
      } catch (error) {
        console.error("Error during login:", error);
        alert("Something went wrong. Please try again later.");
      }
    });
  });

  function handleLogout(){
    localStorage.clear();
    window.location.href = "./login.html";
  }
  
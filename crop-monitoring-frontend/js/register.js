document.addEventListener("DOMContentLoaded", () => {
  const registerForm = document.querySelector("form");

  registerForm.addEventListener("submit", async (e) => {
    e.preventDefault(); 
    
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;
    const repassword = document.getElementById("repassword").value;

    // Validate passwords
    if (password !== repassword) {
      alert("Passwords do not match!");
      return;
    }

    const formData = new FormData();
    formData.append("email", email);
    formData.append("password", password);

    try {
      const response = await fetch(
        "http://localhost:8080/backend/user/register",
        {
          method: "POST",
          body: formData,
        }
      );

      if (response.ok) {
        const data = await response.json();
        alert("Registration successful! Redirecting to login page...");
        window.location.href = "./login.html"; // Redirect to login page
      } else {
        const errorData = await response.json();
        alert(`Error: ${errorData.message || "Registration failed"}`);
      }
    } catch (error) {
      alert("An error occurred while registering. Please try again later.");
      console.error("Registration Error:", error);
    }
  });
});

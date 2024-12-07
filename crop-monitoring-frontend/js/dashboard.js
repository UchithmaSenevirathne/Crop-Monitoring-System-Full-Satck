// dashboard.js
import { roleBasedNavigation } from "./roles.js";

async function getUserRole() {
  return localStorage.getItem("role"); // Default to MANAGER if not set
}

async function getUserName() {
  const email = localStorage.getItem("email");
      const response = await fetch(`http://localhost:8080/staff/getByEmail/${email}`, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
      }
    });

    const data = await response.json();

    console.log(data.firstName);

    return data.firstName;

}

async function buildSidebar() {
  const sidebarContainer = document.querySelector(".sidebar nav");
  // const userNameElement = document.getElementById("userName")
  const userRole = await getUserRole();

  // try {
  //   // Fetch username and update the UI
  //   const userName = await getUserName();
  //   userNameElement.textContent = userName; // Update the text content of the username element
  // } catch (error) {
  //   console.error("Failed to fetch user name:", error);
  //   userNameElement.textContent = "Guest"; // Fallback username
  // }
 
  const navItems = roleBasedNavigation[userRole] || [];
  sidebarContainer.innerHTML = ""; // Clear existing items

  navItems.forEach((item) => {
    const navLink = document.createElement("a");
    // navLink.href = item.link;
    navLink.href = "#";
    navLink.id = item.id;
    navLink.className =
      "flex items-center space-x-3 py-2 px-6 text-white hover:bg-gray-200 rounded-l-full hover:text-gray-600 active:text-[#086568] active:bg-white";
    navLink.innerHTML = `
      <span><i class="${item.icon}"></i></span>
      <span>${item.name}</span>
    `;

    // Add click event listener for dynamic loading
    navLink.addEventListener("click", (e) => {
      e.preventDefault(); // Prevent default behavior
      navigate(item.link, navLink);
    });
    
    sidebarContainer.appendChild(navLink);

    // setUserName();
  });
}

// function setUserName(){
//     const userNameElement = document.getElementById("userName")

//     try {
//     // Fetch username and update the UI
//     const userName = getUserName();
//     userNameElement.textContent = userName; // Update the text content of the username element
//   } catch (error) {
//     console.error("Failed to fetch user name:", error);
//     userNameElement.textContent = "Guest"; // Fallback username
//   }

// }

document.addEventListener("DOMContentLoaded", buildSidebar);

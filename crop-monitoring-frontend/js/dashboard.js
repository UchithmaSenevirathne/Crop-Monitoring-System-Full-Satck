// dashboard.js
import { roleBasedNavigation } from "./roles.js";

async function getUserRole() {
  return localStorage.getItem("role"); // Default to MANAGER if not set
}

async function buildSidebar() {
  const sidebarContainer = document.querySelector(".sidebar nav");
  const userRole = await getUserRole();

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
  });
}

document.addEventListener("DOMContentLoaded", buildSidebar);

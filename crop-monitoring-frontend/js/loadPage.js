// Function to navigate to a page
// loadPage.js
function navigate(page, element) {
    const container = document.getElementById('content-area');

    fetch(page)
        .then((response) => {
            if (!response.ok) {
                throw new Error('Page not found');
            }
            return response.text();
        })
        .then((html) => {
            container.innerHTML = html;
        })
        .catch((error) => {
            container.innerHTML = `<p>Error loading page: ${error.message}</p>`;
        });

    updateActiveNav(element);
}

// Function to update the active nav item
function updateActiveNav(activeElement) {
    const navLinks = document.querySelectorAll('nav a');

    navLinks.forEach(link => {
        link.classList.remove('bg-white', 'text-[#086568]');
        link.classList.add('text-white');
    });

    if (activeElement) {
        activeElement.classList.add('bg-white', 'text-[#086568]');
        activeElement.classList.remove('text-white');
    }
}

// Automatically load the dashboard page on initial load
window.onload = function () {
    const homeNav = document.getElementById('nav-home');

    if (homeNav) {
        homeNav.classList.add('bg-white', 'text-[#086568]');
        homeNav.classList.remove('text-white');
        navigate('home.html', homeNav);
    } else {
        console.error("Element with ID 'nav-home' not found in the DOM.");
    }
};

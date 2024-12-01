function navigate(page, element) {
    // Fetch and display the page content
    const container = document.getElementById('content-area');
    fetch(page)
        .then(response => {
            if (!response.ok) {
                throw new Error('Page not found');
            }
            return response.text();
        })
        .then(html => {
            container.innerHTML = html;
        })
        .catch(error => {
            container.innerHTML = `<p>Error loading page: ${error.message}</p>`;
        });

    // Update active navigation item
    updateActiveNav(element);
}

// Function to update the active nav item
function updateActiveNav(activeElement) {
    // Remove the active class from all links
    const navLinks = document.querySelectorAll('nav a');
    navLinks.forEach(link => {
        link.classList.remove('bg-white', 'text-[#086568]'); // Remove active styles
        link.classList.add('text-white'); // Reset to default styles
    });

    // Add the active class to the clicked link
    activeElement.classList.add('bg-white', 'text-[#086568]');
    activeElement.classList.remove('text-white');
}

// Automatically load the dashboard page on initial load
window.onload = function () {
    const homeNav = document.getElementById('nav-home');
    // Manually set the active state for the home nav link
    homeNav.classList.add('bg-white', 'text-[#086568]');
    homeNav.classList.remove('text-white');
    // Load the home page content
    navigate('home.html', homeNav);
};
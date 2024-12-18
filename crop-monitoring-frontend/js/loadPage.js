// Function to navigate to a page
// loadPage.js
function navigate(page, element) {
    const container = document.getElementById('content-area');

    if (!container) {
        console.error('Content area not found');
        return;
    }

    fetch(page)
        .then((response) => {
            if (!response.ok) {
                throw new Error('Page not found');
            }
            return response.text();
        })
        .then((html) => {
            container.innerHTML = html;

            // Dynamically add event listeners or run scripts after page load
            if (page.includes('field.html')) {
                // Re-run field page specific scripts
                if (typeof loadFields === 'function') {
                    loadFields();
                }
            }else if (page.includes('crop.html')) {
                // Re-run field page specific scripts
                if (typeof loadCrops === 'function') {
                    loadCrops();
                    loadCategoriesAndFields();
                }
            }else if (page.includes('staff.html')) {
                // Re-run field page specific scripts
                if (typeof loadStaffs === 'function') {
                    loadStaffs();
                    loadDesignationAndRole();
                }
            }else if (page.includes('log.html')) {
                // Re-run field page specific scripts
                if (typeof loadLogs === 'function') {
                    loadLogs();
                    loadCropAndStaff();
                }
            }else if (page.includes('equip.html')) {
                // Re-run field page specific scripts
                if (typeof loadEquips === 'function') {
                    loadEquips();
                    loadDropDownEquip();
                }
            }else if (page.includes('vehicle.html')) {
                // Re-run field page specific scripts
                if (typeof loadVehicles === 'function') {
                    loadVehicles();
                    loadDropDownVehicle();
                }
            }else if (page.includes('assign.html')) {
                // Re-run field page specific scripts
                if (typeof loadAssign === 'function') {
                    loadAssign();
                    loadDropDownAssign();
                }
            }
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

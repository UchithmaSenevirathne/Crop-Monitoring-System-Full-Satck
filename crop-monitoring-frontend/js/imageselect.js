// Handle the image change event when a file is selected
function handleImageChange(event) {
    const file = event.target.files[0]; // Get the selected file
    const imageContainer = document.getElementById('imageContainer');
    const cameraIcon = document.getElementById('cameraIcon');
  
    if (file) {
      const reader = new FileReader();
      
      reader.onload = function(e) {
        // Create an image element and set it as the background of the container
        const img = new Image();
        img.src = e.target.result; // Set the image source to the selected file data
        img.classList.add('object-cover', 'w-full', 'h-full'); // Ensure it fills the container
  
        // Clear any previous content (i.e., the camera icon)
        imageContainer.innerHTML = '';
        imageContainer.appendChild(img); // Append the new image
  
        // Hide the camera icon after the image is selected
        cameraIcon.style.display = 'none';
      };
      
      reader.readAsDataURL(file); // Read the file as a data URL
    }
  }
  
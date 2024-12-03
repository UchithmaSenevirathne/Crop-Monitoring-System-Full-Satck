function previewImageCrop(event) {
    const file = event.target.files[0]; // Get the selected file
    if (file) {
      const reader = new FileReader();
    //   reader.onload = function (e) {
    //     const preview = document.getElementById('image-preview-field1'); // Target the preview image element
    //     preview.src = e.target.result; // Set the image source
    //   };
      reader.readAsDataURL(file); // Read the file as a Data URL
    }
  }
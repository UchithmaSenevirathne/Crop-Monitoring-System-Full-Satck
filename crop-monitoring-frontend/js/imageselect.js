const imageInput = document.getElementById("imageInput");
      const imagePreview = document.getElementById("imagePreview");
      const placeholder = document.getElementById("placeholder");

      imageInput.addEventListener("change", () => {
        const file = imageInput.files[0];
        if (file) {
          const reader = new FileReader();
          reader.onload = () => {
            imagePreview.src = reader.result;
            imagePreview.classList.remove("hidden");
            placeholder.classList.add("hidden");
          };
          reader.readAsDataURL(file);
        } else {
          imagePreview.src = "";
          imagePreview.classList.add("hidden");
          placeholder.classList.remove("hidden");
        }
      });
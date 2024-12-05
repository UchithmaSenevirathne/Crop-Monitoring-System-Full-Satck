const apiUrlLog = "http://localhost:8080/log";

let alertTypeLog = "Crop details saved successfully!";

// Utility to convert file to Base64
const getBase64Log = (file) =>
  new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.onload = () => resolve(reader.result.split(",")[1]);
    reader.onerror = (error) => reject(error);
    reader.readAsDataURL(file);
  });

function loadCropAndStaff() {
  // Load crops
  fetch(`${apiUrlCrop}/all_crops`)
    .then((response) => response.json())
    .then((crops) => {
      const cropDropdown = document.getElementById("crops");
      if (!cropDropdown) {
        console.error("Crop dropdown not found");
        return;
      }
      console.log("Crops:", crops);

      // Check if fields have a `name` property
      cropDropdown.innerHTML = crops
        .map(
          (c) => `<option value="${c.cropCode}">${c.cropCommonName}</option>`
        )
        .join("");
    })
    .catch((error) => console.error("Error loading crops:", error));

  // Load staff
  fetch(`${apiUrlStaff}/all_staff`)
    .then((response) => response.json())
    .then((staffs) => {
      const staffDropdown = document.getElementById("staff");
      if (!staffDropdown) {
        console.error("Staff dropdown not found");
        return;
      }
      console.log("Staffs:", staffs);

      // Check if fields have a `name` property
      staffDropdown.innerHTML = staffs
        .map((st) => `<option value="${st.staffId}">${st.firstName}</option>`)
        .join("");
    })
    .catch((error) => console.error("Error loading staffs:", error));

    // Load staff
  fetch(`${apiUrl}/all_fields`)
  .then((response) => response.json())
  .then((fields) => {
    const fieldDropdown = document.getElementById("field");
    if (!fieldDropdown) {
      console.error("Field dropdown not found");
      return;
    }
    console.log("Fields:", fields);

    // Check if fields have a `name` property
    fieldDropdown.innerHTML = fields
      .map((fd) => `<option value="${fd.fieldCode}">${fd.fieldName}</option>`)
      .join("");
  })
  .catch((error) => console.error("Error loading fields:", error));
}

// Function to load all fields and populate the table
async function loadLogs() {
  try {
    const response = await fetch(`${apiUrlLog}/all_logs`);
    const logs = await response.json();

    const tableBody = document.querySelector("tbody");
    if (!tableBody) {
      console.log("Table body not found. Skipping Log population.");
      return;
    }

    tableBody.innerHTML = ""; // Clear existing rows

    logs.forEach((log) => {
      const row = `
        <tr class="border-t border-gray-200 hover:bg-gray-50">
          <td class="px-4 py-2 text-gray-700">${log.logCode}</td>
          <td class="px-4 py-2 text-gray-700">${log.logDate}</td>
          <td class="px-4 py-2 text-gray-700">${log.logDetails}</td>
          <td class="px-4 py-2 text-gray-700">
            <span class="inline-block px-2 py-1 text-xs text-green-700 bg-green-200 rounded-full">${log.cropStatus}</span></td>
          <td class="px-4 py-2 text-gray-700">${log.cropCode}</td>
          <td class="px-4 py-2 text-gray-700">${log.fieldCode}</td>
          <td class="px-4 py-2 text-gray-700">${log.staffId}</td>
          <td class="px-4 py-2 text-gray-700">
            <img src="data:image/png;base64,${log.observedImage}" class="w-16 h-16 rounded" alt="Crop Image" />
          </td>
          <td class="px-4 py-2 text-center">
            <button class="text-blue-500 hover:text-blue-700 mx-2" onclick="editLog(${log.logCode})">
              <i class="fas fa-edit"></i>
            </button>
            <button class="text-red-500 hover:text-red-700 mx-2" onclick="deleteLog(${log.logCode})">
              <i class="fas fa-trash-alt"></i>
            </button>
          </td>
        </tr>`;
      tableBody.insertAdjacentHTML("beforeend", row);
    });
  } catch (error) {
    console.error("Error loading logs:", error);
  }
}

// Save or update field
async function saveOrUpdateLog() {
  const logDate = document.querySelector('input[id="logDate"]').value;
  const logDetails = document.querySelector('textarea[id="logDetails"]').value;
  const observedImage = document.querySelector("#image-upload-log").files[0];
  const cropCode = document.getElementById("crops").value;
  const fieldCode = document.getElementById("field").value;
  const staffId = document.getElementById("staff").value;
  const cropStatus = document.querySelector(
    'input[name="cropStatus"]:checked'
  ).value;
  const button = document.getElementById("btnLog");

  try {
    const logImage = observedImage ? await getBase64Log(observedImage) : null;

    console.log(fieldCode);
    console.log(cropCode);
    console.log(staffId);

    const method = button.dataset.mode === "edit" ? "PUT" : "POST";
    alertTypeLog =
      button.dataset.mode === "edit"
        ? "Log updated successfully!"
        : "Crop details saved successfully!";
    const logCode = button.dataset.logCode || "";
    const url =
      method === "POST" ? apiUrlLog : `${apiUrlLog}/update/${logCode}`;
    const formData = new FormData();

    formData.append("logDate", logDate);
    formData.append("logDetails", logDetails);
    formData.append("cropStatus", cropStatus);
    if (logImage) formData.append("observedImage", observedImage);
    formData.append("staffId", staffId);
    formData.append("fieldCode", fieldCode);
    formData.append("cropCode", cropCode);

    await fetch(url, { method, body: formData });
    alert(alertTypeLog);
    resetFormLog();
    loadLogs();
  } catch (error) {
    console.error("Error saving log:", error);
  }
}

// Edit field
async function editLog(logCode) {
  console.log(logCode);
  try {
    const response = await fetch(`${apiUrlLog}/get/${logCode}`);
    const log = await response.json();

    document.querySelector('input[id="logDate"]').value = log.logDate
    document.querySelector('textarea[id="logDetails"]').value = log.logDetails;
    // document.querySelector("#image-upload-log").files[0] = log.observedImage;
    // document.querySelector('input[name="cropStatus"]:checked').value = log.cropStatus;
    // document.querySelector('input[id="crops"]').value = log.cropCode;
    // document.querySelector('input[id="field"]').value = log.fieldCode;
    // document.querySelector('input[id="staff"]').value = log.staffId;

    // Handle image 
    const imageUpload = document.getElementById('image-upload-log');
    if (log.observedImage) {
      // Convert base64 to File object
      const byteCharacters = atob(log.observedImage);
      const byteNumbers = new Array(byteCharacters.length);
      for (let i = 0; i < byteCharacters.length; i++) {
        byteNumbers[i] = byteCharacters.charCodeAt(i);
      }
      const byteArray = new Uint8Array(byteNumbers);
      const blob = new Blob([byteArray], { type: 'image/png' }); // Adjust mime type if needed
      
      // Create a File object
      const file = new File([blob], 'log-image.png', { type: 'image/png' });
      
      // Create a DataTransfer object to set files
      const dataTransfer = new DataTransfer();
      dataTransfer.items.add(file);
      imageUpload.files = dataTransfer.files;

      // Optional: Show image preview
      const preview = document.getElementById('image-preview-log');
      if (preview) {
        preview.src = `data:image/png;base64,${log.observedImage}`;
      }
    }

    // Set select fields
    const cropDropdown = document.getElementById('crops');
    if (cropDropdown) {
      cropDropdown.value = log.cropCode;
    }

    const fieldDropdown = document.getElementById('field');
    if (fieldDropdown) {
      fieldDropdown.value = log.fieldCode;
    }

    const staffDropdown = document.getElementById('staff');
    if (staffDropdown) {
      staffDropdown.value = log.staffId;
    }

    // Set radio button for crop season
    const statusRadios = document.querySelectorAll('input[name="cropStatus"]');
    statusRadios.forEach(radio => {
      if (radio.value === log.cropStatus) {
        radio.checked = true;
      }
    });

    const button = document.getElementById("btnLog");
    button.textContent = "Update Log";
    button.dataset.mode = "edit";
    button.dataset.logCode = logCode;
  } catch (error) {
    console.error("Error editing Log:", error);
  }
}

// Delete field
async function deleteLog(logCode) {
  if (!confirm("Are you sure to delete this data?")) return;

  try {
    await fetch(`${apiUrlLog}/delete/${logCode}`, { method: "DELETE" });
    alert("Log deleted successfully!");
    loadLogs();
  } catch (error) {
    console.error("Error deleting log:", error);
  }
}

// Reset form to default state
function resetFormLog() {
    document.querySelector('input[id="logDate"]').value = ""
    document.querySelector('textarea[id="logDetails"]').value = "";
    document.querySelector("#image-upload-log").files[0] = "";
    document.querySelector('input[name="cropStatus"]:checked').value = "";

  const button = document.querySelector("button");
  button.textContent = "Create Log";
  button.dataset.mode = "add";
  button.removeAttribute("data-log-code");
}

// Load fields on page load
document.addEventListener("DOMContentLoaded", () => {
  loadCropAndStaff();
  // Only run these if the elements exist
  const tableBody = document.querySelector("tbody");
  if (tableBody) {
    loadLogs();
  }

  const saveButtonLog = document.getElementById("btnLog");
  if (saveButtonLog) {
    saveButtonLog.addEventListener("click", saveOrUpdateLog);
  } else {
    console.log(
      "Save button not found. This might be okay if not on the Log page."
    );
  }
});

// Attach preview functions to the window object to make them globally accessible
window.previewImageLog = function (event) {
  const file = event.target.files[0]; // Get the selected file
  if (file) {
    const reader = new FileReader();
    reader.onload = function (e) {
      // Optional: If you want to show a preview, uncomment and add an img element with id='image-preview-field1'
      // const preview = document.getElementById('image-preview-field1');
      // preview.src = e.target.result;
      console.log("Log image selected:", file.name);
    };
    reader.readAsDataURL(file); // Read the file as a Data URL
  }
};

const apiUrlVehicle = "http://localhost:8080/vehicle";

// const API_BASE_URL_FIELD = "http://localhost:8080/field"

let alertTypeVehicle = "Vehicle register successfully!";

function loadDropDownVehicle() {
  // Load cat
  fetch(`${apiUrlVehicle}/categories`)
    .then(response => response.json())
    .then(vehicles => {
      const vehicleDropdown = document.getElementById("categories");
      if (!vehicleDropdown) {
        console.error("Category dropdown not found");
        return;
      }
      console.log("Cat:", vehicles);

      // Check if categories is an array of strings or objects
      vehicleDropdown.innerHTML = vehicles.map(veh =>
        typeof veh === "string" 
          ? `<option value="${veh}">${veh}</option>` 
          : `<option value="${veh.id || veh.name}">${veh.name}</option>`
      ).join('');
    })
    .catch(error => console.error("Error loading categories:", error));

     // Load status
  fetch(`${apiUrlVehicle}/status`)
  .then(response => response.json())
  .then(statuses => {
    const statusDropdown = document.getElementById("status");
    if (!statusDropdown) {
      console.error("Status dropdown not found");
      return;
    }
    console.log("Status:", statuses);

    // Check if categories is an array of strings or objects
    statusDropdown.innerHTML = statuses.map(state =>
      typeof state === "string" 
        ? `<option value="${state}">${state}</option>` 
        : `<option value="${state.id || state.name}">${state.name}</option>`
    ).join('');
  })
  .catch(error => console.error("Error loading status:", error));

    // Load staff
  fetch(`${apiUrlStaff}/all_staff`)
  .then((response) => response.json())
  .then((staffs) => {
    const staffDropdown = document.getElementById("staffs");
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
}

// Function to load all fields and populate the table
async function loadVehicles() {
  try {
    const response = await fetch(`${apiUrlVehicle}/all_vehicles`);
    const vehicles = await response.json();

    const tableBody = document.querySelector("tbody");
    if (!tableBody) {
      console.log("Table body not found. Skipping Vehicle population.");
      return;
    }
    
    tableBody.innerHTML = ""; // Clear existing rows

    vehicles.forEach((vh) => {
      const row = `
        <tr class="border-t border-gray-200 hover:bg-gray-50">
          <td class="px-4 py-2 text-gray-700">${vh.vehicleCode}</td>
          <td class="px-4 py-2 text-gray-700">${vh.licensePlateNumber}</td>
          <td class="px-4 py-2 text-gray-700">${vh.vehicleCategory}</td>
          <td class="px-4 py-2 text-gray-700">${vh.fuelType}</td>
          <td class="px-4 py-2 text-gray-700">
            <span class="inline-block px-2 py-1 text-xs text-green-700 bg-green-200 rounded-full">${vh.status}</span>
            </td>
          <td class="px-4 py-2 text-gray-700">${vh.remarks}</td>
          <td class="px-4 py-2 text-gray-700">${vh.staffId}</td>
          <td class="px-4 py-2 text-center">
            <button class="text-blue-500 hover:text-blue-700 mx-2" onclick="editVehicle(${vh.vehicleCode})">
              <i class="fas fa-edit"></i>
            </button>
            <button class="text-red-500 hover:text-red-700 mx-2" onclick="deleteVehicle(${vh.vehicleCode})">
              <i class="fas fa-trash-alt"></i>
            </button>
          </td>
        </tr>`;
      tableBody.insertAdjacentHTML("beforeend", row);
    });
  } catch (error) {
    console.error("Error loading vehicles:", error);
  }
}

// Save or update field
async function saveOrUpdateVehicle() {
  const licensePlateNumber = document.getElementById('licensePlateNumber').value;
  const vehicleCategory = document.getElementById('categories').value;
  const fuelType = document.querySelector('input[name="type"]:checked').value;
  const status = document.getElementById('status').value;
  const remarks = document.getElementById('remarks').value;
  const staffId = document.getElementById('staffs').value;
  const button = document.getElementById("btnVehicle");

  try {
   console.log(staffId);

    const method = button.dataset.mode === "edit" ? "PUT" : "POST";
    alertTypeVehicle = button.dataset.mode === "edit" ? "Vehicle updated successfully!" : "Vehicle register successfully!"
    const vehicleCode = button.dataset.vehicleCode || "";
    const url = method === "POST" ? apiUrlVehicle : `${apiUrlVehicle}/update/${vehicleCode}`;

    const vehicleData = {
        licensePlateNumber,
        vehicleCategory,
        fuelType,
        status,
        remarks,
        staffId
      };
  
      // Send JSON data
      await fetch(url, {
        method,
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(vehicleData),
      });

    alert(alertTypeVehicle);
    resetFormVehicle();
    loadVehicles();
  } catch (error) {
    console.error("Error saving vehicles:", error);
  }
}

// Edit equip
async function editVehicle(vehicleCode) {
  console.log(vehicleCode)
  try {
    const response = await fetch(`${apiUrlVehicle}/get/${vehicleCode}`);
    const vehicle = await response.json();

    document.getElementById('licensePlateNumber').value = vehicle.licensePlateNumber;
    // document.getElementById('categories').value = vehicle.vehicleCategory;
    // document.querySelector('input[name="type"]:checked').value = vehicle.fuelType;
    // document.getElementById('status').value = vehicle.status;
    document.getElementById('remarks').value = vehicle.remarks;
    // document.getElementById('staffs').value = vehicle.staffId;

     // Set select fields
     const vehicleDropdown = document.getElementById('categories');
     if (vehicleDropdown) {
      vehicleDropdown.value = vehicle.vehicleCategory;
     }

     const statuses = document.getElementById('status');
     if (statuses) {
      statuses.value = vehicle.status;
     }

     const staffDropdown = document.getElementById('staffs');
     if (staffDropdown) {
      staffDropdown.value = vehicle.staffId;
     }

     // Set radio button for crop season
    const typeRadios = document.querySelectorAll('input[name="type"]');
    typeRadios.forEach(radio => {
      if (radio.value === vehicle.fuelType) {
        radio.checked = true;
      }
    });

    const button = document.getElementById("btnVehicle");
    button.textContent = "Update Vehicle";
    button.dataset.mode = "edit";
    button.dataset.vehicleCode = vehicleCode;
  } catch (error) {
    console.error("Error editing Vehicle:", error);
  }
}

// Delete equip
async function deleteVehicle(vehicleCode) {
  if (!confirm("Are you sure to delete this data?")) return;

  try {
    await fetch(`${apiUrlVehicle}/delete/${vehicleCode}`, { method: "DELETE" });
    alert("Vehicle deleted successfully!");
    loadVehicles();
  } catch (error) {
    console.error("Error deleting vehicle:", error);
  }
}

// Reset form to default state
function resetFormVehicle() {
    document.getElementById('licensePlateNumber').value = "";
    document.getElementById('remarks').value = "";

  const button = document.querySelector("button");
  button.textContent = "Add Vehicle";
  button.dataset.mode = "add";
  button.removeAttribute("data-vehicle-code");
}

// Load fields on page load
document.addEventListener("DOMContentLoaded", () => {

  loadDropDownVehicle();
  // Only run these if the elements exist
  const tableBody = document.querySelector("tbody");
  if (tableBody) {
    loadVehicles();
  }

  const saveButtonVehi = document.getElementById("btnVehicle");
  if (saveButtonVehi) {
    saveButtonVehi.addEventListener("click", saveOrUpdateVehicle);
  } else {
    console.log("Save button not found. This might be okay if not on the Vehicle page.");
  }
});

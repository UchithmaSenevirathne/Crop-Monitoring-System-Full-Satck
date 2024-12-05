const apiUrlEquip = "http://localhost:8080/equipment";

// const API_BASE_URL_FIELD = "http://localhost:8080/field"

let alertTypeEquip = "Equipment add successfully!";

function loadDropDownEquip() {
  // Load names
  fetch(`${apiUrlEquip}/names`)
    .then(response => response.json())
    .then(names => {
      const namesDropdown = document.getElementById("names");
      if (!namesDropdown) {
        console.error("Names dropdown not found");
        return;
      }
      console.log("Names:", names);

      // Check if categories is an array of strings or objects
      namesDropdown.innerHTML = names.map(n =>
        typeof n === "string" 
          ? `<option value="${n}">${n}</option>` 
          : `<option value="${n.id || n.name}">${n.name}</option>`
      ).join('');
    })
    .catch(error => console.error("Error loading names:", error));

     // Load status
  fetch(`${apiUrlEquip}/status`)
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

  // Load fields
  fetch(`${apiUrl}/all_fields`)
    .then(response => response.json())
    .then(fieldNames => {
      const fieldDropdown = document.getElementById("fields");
      if (!fieldDropdown) {
        console.error("Field dropdown not found");
        return;
      }
      console.log("Fields:", fieldNames);

      // Check if fields have a `name` property
      fieldDropdown.innerHTML = fieldNames.map(field => 
        `<option value="${field.fieldCode}">${field.fieldName}</option>`
      ).join('');
    })
    .catch(error => console.error("Error loading fields:", error));

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
async function loadEquips() {
  try {
    const response = await fetch(`${apiUrlEquip}/all_equip`);
    const equips = await response.json();

    const tableBody = document.querySelector("tbody");
    if (!tableBody) {
      console.log("Table body not found. Skipping Equipment population.");
      return;
    }
    
    tableBody.innerHTML = ""; // Clear existing rows

    equips.forEach((eq) => {
      const row = `
        <tr class="border-t border-gray-200 hover:bg-gray-50">
          <td class="px-4 py-2 text-gray-700">${eq.equipmentId}</td>
          <td class="px-4 py-2 text-gray-700">${eq.name}</td>
          <td class="px-4 py-2 text-gray-700">${eq.type}</td>
          <td class="px-4 py-2 text-gray-700">
            <span class="inline-block px-2 py-1 text-xs text-green-700 bg-green-200 rounded-full">${eq.status}</span>
            </td>
          <td class="px-4 py-2 text-gray-700">${eq.fieldCode}</td>
          <td class="px-4 py-2 text-gray-700">${eq.staffId}</td>
          <td class="px-4 py-2 text-center">
            <button class="text-blue-500 hover:text-blue-700 mx-2" onclick="editEquip(${eq.equipmentId})">
              <i class="fas fa-edit"></i>
            </button>
            <button class="text-red-500 hover:text-red-700 mx-2" onclick="deleteEquip(${eq.equipmentId})">
              <i class="fas fa-trash-alt"></i>
            </button>
          </td>
        </tr>`;
      tableBody.insertAdjacentHTML("beforeend", row);
    });
  } catch (error) {
    console.error("Error loading equipments:", error);
  }
}

// Save or update field
async function saveOrUpdateEquip() {
  const name = document.getElementById('names').value;
  const type = document.querySelector('input[name="type"]:checked').value;
  const status = document.getElementById('status').value;
  const fieldCode = document.getElementById('fields').value;
  const staffId = document.getElementById('staffs').value;
  const button = document.getElementById("btnEquip");

  try {
   console.log(fieldCode);
   console.log(staffId);

    const method = button.dataset.mode === "edit" ? "PUT" : "POST";
    alertTypeEquip = button.dataset.mode === "edit" ? "Equipment updated successfully!" : "Equipment add successfully!"
    const equipmentId = button.dataset.equipmentId || "";
    const url = method === "POST" ? apiUrlEquip : `${apiUrlEquip}/update/${equipmentId}`;

    const equipData = {
        name,
        type,
        status,
        fieldCode,
        staffId
      };
  
      // Send JSON data
      await fetch(url, {
        method,
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(equipData),
      });

    alert(alertTypeEquip);
    resetFormEquip();
    loadEquips();
  } catch (error) {
    console.error("Error saving equipments:", error);
  }
}

// Edit equip
async function editEquip(equipmentId) {
  console.log(equipmentId)
  try {
    const response = await fetch(`${apiUrlEquip}/get/${equipmentId}`);
    const equip = await response.json();

    document.getElementById('names').value = equip.names;
    document.querySelector('input[name="type"]:checked').value = equip.type;
    document.getElementById('status').value = equip.status;
    document.getElementById('fields').value = equip.fieldCode;
    document.getElementById('staffs').value = equip.staffId;

    const button = document.getElementById("btnEquip");
    button.textContent = "Update Equipment";
    button.dataset.mode = "edit";
    button.dataset.equipmentId = equipmentId;
  } catch (error) {
    console.error("Error editing Equipment:", error);
  }
}

// Delete equip
async function deleteEquip(equipmentId) {
  if (!confirm("Are you sure to delete this data?")) return;

  try {
    await fetch(`${apiUrlEquip}/delete/${equipmentId}`, { method: "DELETE" });
    alert("Equipment deleted successfully!");
    loadEquips();
  } catch (error) {
    console.error("Error deleting equip:", error);
  }
}

// Reset form to default state
function resetFormEquip() {
  const button = document.querySelector("button");
  button.textContent = "Add Equipment";
  button.dataset.mode = "add";
  button.removeAttribute("data-equipment-code");
}

// Load fields on page load
document.addEventListener("DOMContentLoaded", () => {

  loadDropDownEquip();
  // Only run these if the elements exist
  const tableBody = document.querySelector("tbody");
  if (tableBody) {
    loadEquips();
  }

  const saveButtonEquip = document.getElementById("btnEquip");
  if (saveButtonEquip) {
    saveOrUpdateEquip.addEventListener("click", saveOrUpdateEquip);
  } else {
    console.log("Save button not found. This might be okay if not on the Equip page.");
  }
});

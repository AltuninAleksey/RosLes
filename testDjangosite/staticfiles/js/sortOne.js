document.addEventListener("DOMContentLoaded", function() {
  let table = document.querySelector("table");
  let tbody = table.querySelector("tbody");
  let ths = table.querySelectorAll("th");

  ths.forEach(th => {
    th.addEventListener("click", () => {
      let index = Array.from(ths).indexOf(th);
      let rows = Array.from(tbody.querySelectorAll("tr"));

      rows.sort((a, b) => {
        let aVal = a.cells[index].textContent.toLowerCase();
        let bVal = b.cells[index].textContent.toLowerCase();
        return aVal.localeCompare(bVal);
      });

      tbody.innerHTML = "";
      rows.forEach(row => {
        tbody.appendChild(row);
      });
    });
  });
});

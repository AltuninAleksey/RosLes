var myList = $('#myTable');
var listItems = myList.find('tr');
listItems.sort(function(a, b) {
     return $(a).text().toUpperCase().localeCompare($(b).text().toUpperCase());
})
$.each(listItems, function(idx, itm) { myList.append(itm); });
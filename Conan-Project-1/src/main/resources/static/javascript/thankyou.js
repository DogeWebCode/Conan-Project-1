const orderNumber = sessionStorage.getItem("orderNumber");

if (orderNumber) {
  const orderNumberElement = document.querySelector("#order-number");
  orderNumberElement.textContent = orderNumber;
  sessionStorage.removeItem("orderNumber");
} else {
  orderNumberElement.textContent = "神秘訂單";
}

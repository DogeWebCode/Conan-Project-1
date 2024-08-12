const baseUrl = "http://3.113.167.117";
const localUrl = "http://localhost:8080";

let productData = null;

// -------------------------Get Productinfo-------------------------
// get Id from url
document.addEventListener("DOMContentLoaded", getIdFromUrl);

async function getIdFromUrl() {
  const urlParams = new URLSearchParams(window.location.search);
  const productId = urlParams.get("id");

  if (productId) {
    await fetchProductDetails(productId);
  } else {
    console.error("No product id in url");
  }
}

async function fetchProductDetails(id) {
  try {
    const response = await fetch(
      `${baseUrl}/api/1.0/products/details?id=${id}`
    );
    if (!response.ok) {
      throw new Error("Network response was not ok");
    }
    const { data } = await response.json();
    displayProductDetails(data);
  } catch (error) {
    console.error("Error fetching data");
    console.error(error);
  }
}

// -------------------------Display Productinfo-------------------------
function displayProductDetails(product) {
  productData = product;

  // main image
  document.getElementById("main-image").src = product.main_image;

  // product info
  document.getElementById("product-title").textContent = product.title;
  document.getElementById("product-id").textContent = product.id;
  document.getElementById("product-price").textContent = `TWD.${product.price}`;

  // colors
  const colorOptions = document.getElementById("color-options");
  product.colors.forEach((color) => {
    const colorBox = document.createElement("div");
    colorBox.className = "color-box";
    colorBox.style.backgroundColor = color.code;
    colorBox.title = color.name;
    colorBox.dataset.color = color.code;
    colorOptions.appendChild(colorBox);
  });

  // sizes
  const sizeOptions = document.getElementById("size-options");
  product.sizes.forEach((size) => {
    const sizeDiv = document.createElement("div");
    sizeDiv.className = "size-box";
    sizeDiv.textContent = size;
    sizeDiv.value = size;
    sizeOptions.appendChild(sizeDiv);
  });

  // product details
  document.getElementById("note").textContent = product.note;
  document.getElementById("texture").textContent = product.texture;
  document.getElementById("place").textContent = product.place;

  // story
  document.getElementById("product-story-content").textContent = product.story;

  // images
  const images = document.getElementById("extra-images");
  product.images.forEach((image) => {
    const img = document.createElement("img");
    img.src = image;
    img.alt = "product image";
    img.className = "extra-image";
    images.appendChild(img);
  });
}

document.addEventListener("DOMContentLoaded", function () {
  const checkoutBtn = document.getElementById("checkout");
  const paymentModal = $("#paymentModal");

  if (checkoutBtn && paymentModal.length) {
    checkoutBtn.addEventListener("click", function () {
      const validToken = getValidToken();
      if (!this.disabled && validToken) {
        paymentModal.modal("show");
      } else {
        alert("請先登入會員");
        window.location.href = "profile.html";
      }
    });

    paymentModal.one("shown.bs.modal", function () {
      initTapPay();
    });
  } else {
    console.error("Checkout button or payment modal not found");
  }

  document
    .getElementById("decrease")
    .addEventListener("click", decreaseQuantity);
  document
    .getElementById("increase")
    .addEventListener("click", increaseQuantity);

  updateQuantityDisplay();

  function updateCheckoutButton() {
    const colorSelected = document.querySelector(".color-box.active");
    const sizeSelected = document.querySelector(".size-box.active");

    if (colorSelected && sizeSelected && productData) {
      const colorCode = colorSelected.dataset.color;
      const size = sizeSelected.textContent;
      const variant = productData.variants.find(
        (v) => v.color_code === colorCode && v.size === size
      );

      if (variant && variant.stock > 0) {
        checkoutBtn.disabled = false;
        checkoutBtn.style.backgroundColor = "";
        checkoutBtn.textContent = "確定結帳";
        setMaxStock(variant.stock);
      } else {
        checkoutBtn.disabled = true;
        checkoutBtn.style.backgroundColor = "gray";
        checkoutBtn.textContent = "庫存不足";
        setMaxStock(0);
      }
    } else {
      checkoutBtn.disabled = true;
      checkoutBtn.style.backgroundColor = "gray";
      checkoutBtn.textContent = "請選擇顏色和尺寸";
      setMaxStock(0);
    }
  }

  document.addEventListener("click", function (event) {
    if (event.target.classList.contains("color-box")) {
      document.querySelectorAll(".color-box").forEach(function (box) {
        document.querySelectorAll(".size-box").forEach(function (sizebox) {
          sizebox.classList.remove("active");
        });
        box.classList.remove("active");
      });
      event.target.classList.add("active");
      updateCheckoutButton();
    }
  });

  document.addEventListener("click", function (event) {
    if (event.target.classList.contains("size-box")) {
      document.querySelectorAll(".size-box").forEach(function (box) {
        box.classList.remove("active");
      });
      event.target.classList.add("active");
      updateCheckoutButton();
    }
  });

  // loading page default select color
  window.onload = function () {
    document.querySelectorAll(".color-box")[0].click();
  };
});

// -------------------------Quantity Control-------------------------
let currentQuantity = 1;
let maxStock = 0;

function updateQuantityDisplay() {
  const quantityElement = document.getElementById("quantity");
  const decreaseButton = document.getElementById("decrease");
  const increaseButton = document.getElementById("increase");

  quantityElement.textContent = currentQuantity;

  if (maxStock > 0) {
    decreaseButton.classList.toggle("disabled", currentQuantity <= 1);
    increaseButton.classList.toggle("disabled", currentQuantity >= maxStock);
    quantityElement.textContent = currentQuantity;
  } else {
    quantityElement.textContent = "-";
    decreaseButton.classList.add("disabled");
    increaseButton.classList.add("disabled");
  }
}

function decreaseQuantity() {
  if (currentQuantity > 1) {
    currentQuantity--;
    updateQuantityDisplay();
  }
}

function increaseQuantity() {
  if (currentQuantity < maxStock) {
    currentQuantity++;
    updateQuantityDisplay();
  }
}

function setMaxStock(stock) {
  maxStock = stock;
  if (maxStock > 0) {
    currentQuantity = 1;
  } else {
    currentQuantity = 0;
  }
  currentQuantity = Math.min(currentQuantity, maxStock);
  updateQuantityDisplay();
}
// -------------------------Valid token---------------------------

function isTokenExpired(token) {
  if (!token) {
    return true;
  }
  try {
    const payload = token.split(".")[1];
    const decodeJson = atob(payload);
    const decode = JSON.parse(decodeJson);
    const exp = decode.exp;

    const currentTime = Math.floor(Date.now() / 1000);
    return currentTime > exp;
  } catch (e) {
    return true;
  }
}

function getValidToken() {
  const token = localStorage.getItem("access_token");
  if (isTokenExpired(token)) {
    localStorage.removeItem("access_token");
    return null;
  }
  return token;
}

// -------------------------TAPPAY Submit-------------------------
function initTapPay() {
  // Setup TapPay SDK
  TPDirect.setupSDK(
    12348,
    "app_pa1pQcKoY22IlnSXq5m5WP5jFKzoRG58VEXpT7wU62ud7mMbDOGzCYIlzzLF",
    "sandbox"
  );

  // Setup TapPay card fields
  TPDirect.card.setup({
    fields: {
      number: {
        element: ".form-control.card-number",
        placeholder: "**** **** **** ****",
      },
      expirationDate: {
        element: document.getElementById("tappay-expiration-date"),
        placeholder: "MM / YY",
      },
      ccv: {
        element: $(".form-control.ccv")[0],
        placeholder: "後三碼",
      },
    },
    styles: {
      input: {
        color: "gray",
      },
      "input.ccv": {},
      ":focus": {
        color: "black",
      },
      ".valid": {
        color: "green",
      },
      ".invalid": {
        color: "red",
      },
      "@media screen and (max-width: 400px)": {
        input: {
          color: "orange",
        },
      },
    },
    isMaskCreditCardNumber: true,
    maskCreditCardNumberRange: {
      beginIndex: 6,
      endIndex: 11,
    },
  });

  // Handle card field updates
  TPDirect.card.onUpdate(function (update) {
    if (update.canGetPrime) {
      $('button[type="submit"]').removeAttr("disabled");
    } else {
      $('button[type="submit"]').attr("disabled", true);
    }

    // Display card type
    var newType = update.cardType === "unknown" ? "" : update.cardType;
    $("#cardtype").text(newType);

    // Update form group styles based on validation
    if (update.status.number === 2) {
      setNumberFormGroupToError(".card-number-group");
    } else if (update.status.number === 0) {
      setNumberFormGroupToSuccess(".card-number-group");
    } else {
      setNumberFormGroupToNormal(".card-number-group");
    }

    if (update.status.expiry === 2) {
      setNumberFormGroupToError(".expiration-date-group");
    } else if (update.status.expiry === 0) {
      setNumberFormGroupToSuccess(".expiration-date-group");
    } else {
      setNumberFormGroupToNormal(".expiration-date-group");
    }

    if (update.status.ccv === 2) {
      setNumberFormGroupToError(".ccv-group");
    } else if (update.status.ccv === 0) {
      setNumberFormGroupToSuccess(".ccv-group");
    } else {
      setNumberFormGroupToNormal(".ccv-group");
    }
  });

  // Handle form submission
  $("form").on("submit", function (event) {
    event.preventDefault();

    forceBlurIos();

    const tappayStatus = TPDirect.card.getTappayFieldsStatus();
    if (tappayStatus.canGetPrime === false) {
      alert("can not get prime");
      return;
    }

    TPDirect.card.getPrime(function (result) {
      if (result.status !== 0) {
        alert("get prime error " + result.msg);
        return;
      }
      submitOrder(result.card.prime);
    });
  });
}

function submitOrder(prime) {
  const selectedColor = document.querySelector(".color-box.active");
  const selectedSize = document.querySelector(".size-box.active");
  const quantity = parseInt(document.getElementById("quantity").textContent);

  // calculate price
  const subtotal = productData.price * quantity;
  const freight = 30;
  const total = subtotal + freight;

  const order = {
    prime: prime,
    order: {
      shipping: "delivery",
      payment: "credit_card",
      subtotal: subtotal,
      freight: freight,
      total: total,
      recipient: {
        name: "許阿柴",
        phone: "0987654321",
        email: "james010203@gmail.com",
        address: "台北市信義區市府路1號",
        time: "morning",
      },
      list: [
        {
          id: productData.id.toString(),
          name: productData.title,
          price: productData.price,
          color: {
            code: selectedColor.dataset.color,
            name: selectedColor.title,
          },
          size: selectedSize.textContent,
          qty: quantity,
        },
      ],
    },
  };

  fetch(`${baseUrl}/api/1.0/order/checkout`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: "Bearer " + localStorage.getItem("access_token"),
    },
    body: JSON.stringify(order),
  })
    .then((response) => response.json())
    .then((data) => {
      sessionStorage.setItem("orderNumber", data.data.number);
      console.log(data.number);
      window.location.href = "thankyou.html";
    })
    .catch((e) => {
      console.log(e);
      alert("購買商品失敗！請重新結帳");
    });
}

function setNumberFormGroupToError(selector) {
  $(selector).addClass("has-error");
  $(selector).removeClass("has-success");
}

function setNumberFormGroupToSuccess(selector) {
  $(selector).removeClass("has-error");
  $(selector).addClass("has-success");
}

function setNumberFormGroupToNormal(selector) {
  $(selector).removeClass("has-error");
  $(selector).removeClass("has-success");
}

function forceBlurIos() {
  if (!isIos()) {
    return;
  }
  var input = document.createElement("input");
  input.setAttribute("type", "text");
  // Insert to active element to ensure scroll lands somewhere relevant
  document.activeElement.prepend(input);
  input.focus();
  input.parentNode.removeChild(input);
}

function isIos() {
  return /iPad|iPhone|iPod/.test(navigator.userAgent) && !window.MSStream;
}

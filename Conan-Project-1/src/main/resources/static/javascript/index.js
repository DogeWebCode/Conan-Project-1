const baseUrl = "http://3.113.167.117";
const localUrl = "http://localhost:8080";

// get category from url
function getCategoryFromUrl() {
  const urlParams = new URLSearchParams(window.location.search);
  return urlParams.get("category");
}

function getApiUrl() {
  const category = getCategoryFromUrl();
  if (category) {
    return `${baseUrl}/api/1.0/products/${category}`;
  }
  // default api
  return `${baseUrl}/api/1.0/products/all`;
}

// fetch products from category
function fetchAndRenderProducts() {
  const apiUrl = getApiUrl();

  fetch(apiUrl)
    .then((response) => {
      if (!response.ok) {
        throw new Error("Network response was not ok");
      }
      return response.json();
    })
    .then((responseData) => {
      const productsContainer = document.querySelector(".products");
      productsContainer.innerHTML = ""; // clear products

      responseData.data.forEach((product) => {
        const productElement = createProductElement(product);
        productsContainer.appendChild(productElement);
      });
    })
    .catch((error) => {
      console.log("Error fetching data");
      console.error(error);
    });
}

// fetch products when page loaded
window.addEventListener("load", fetchAndRenderProducts);

//get campaign image & story
fetch(`${baseUrl}/api/1.0/marketing/campaigns`, {
  method: "GET",
  headers: {
    Accept: "application/json",
  },
})
  .then((response) => {
    if (!response.ok) {
      throw new Error("Network response was not ok");
    }
    return response.json();
  })
  .then((responseData) => {
    const data = responseData.data;
    const campaignsContainer = document.querySelector(".slider");

    campaignsContainer.innerHTML = "";

    if (data.length > 0) {
      const firstSlider = data[0];
      const campaignElement = createCampaignElement(firstSlider);
      campaignsContainer.appendChild(campaignElement);
    }
  })
  .catch((error) => {
    console.log("Error fetching data");
    console.error(error);
  });

// create product
function createProductElement(product) {
  const productElement = document.createElement("a");
  productElement.className = "product-item";
  productElement.href = `product.html?id=${product.id}`;

  const img = document.createElement("img");
  img.src = product.main_image;
  img.alt = product.title;
  productElement.appendChild(img);

  const colorDiv = document.createElement("div");
  colorDiv.className = "color-options";
  product.colors.forEach((color) => {
    const colorBox = document.createElement("span");
    colorBox.style.backgroundColor = color.code;
    colorBox.title = color.name;
    colorDiv.appendChild(colorBox);
  });
  productElement.appendChild(colorDiv);

  const title = document.createElement("h3");
  title.textContent = product.title;
  productElement.appendChild(title);

  const price = document.createElement("p");
  price.textContent = `TWD.${product.price}`;
  productElement.appendChild(price);

  return productElement;
}

function createCampaignElement(slider) {
  const campaignsElement = document.createElement("a");
  campaignsElement.className = "slider-content";
  campaignsElement.href = `product.html?id=${slider.product_id}`;

  const img = document.createElement("img");
  img.src = slider.picture;
  img.alt = "campaign";
  img.className = "slider-img";
  campaignsElement.appendChild(img);

  const storyContainer = document.createElement("div");
  storyContainer.className = "story-container";

  const lines = slider.story.split("\r\n");
  lines.forEach((line, index) => {
    const story = document.createElement("h2");
    story.textContent = line;
    story.className = `slider-text line-${index}`;
    storyContainer.appendChild(story);
  });

  campaignsElement.appendChild(storyContainer);

  return campaignsElement;
}

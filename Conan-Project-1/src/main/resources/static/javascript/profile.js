const baseUrl = "http://3.113.167.117";
const localUrl = "http://localhost:8080";

// ----------------- Register -----------------
$(document).ready(function () {
  $("#register-form").on("submit", function (e) {
    e.preventDefault();

    //get user input val
    const username = $("#user-register-username").val();
    const email = $("#user-register-email").val();
    const password = $("#user-register-password").val();

    // create data object
    const data = {
      name: username,
      email: email,
      password: password,
    };

    fetch(`${baseUrl}/api/1.0/user/signup`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(data),
    })
      .then((response) => response.json())
      .then((data) => {
        if (data.error) {
          $(".register-message")
            .text(`註冊失敗：${data.error}`)
            .css("color", "red");
        } else {
          $(".register-message").text(`註冊成功！`).css("color", "green");

          setTimeout(function () {
            $("#registerModal").modal("hide");
          }, 1000);
        }

        // clean register form after submit
        $("#register-form")[0].reset();
      });
  });
});

// ----------------- Login -----------------
$(document).ready(function () {
  $("#login-form").on("submit", function (e) {
    e.preventDefault();

    //get user input val
    const email = $("#user-login-email").val();
    const password = $("#user-login-password").val();

    // create data object
    const data = {
      provider: "native",
      email: email,
      password: password,
    };

    fetch(`${baseUrl}/api/1.0/user/signin`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(data),
    })
      .then((response) => response.json())
      .then((data) => {
        if (data.error) {
          $(".login-message")
            .text(`登入失敗：${data.error}`)
            .css("color", "red");
        } else {
          $(".login-message").text(`登入成功！`).css("color", "green");
          // save token to localStorage
          localStorage.setItem("access_token", data.data.access_token);

          $("#login-form").hide();

          fetchUserProfile(data.data.access_token);
        }

        // clean login form after submit
        $("#login-form")[0].reset();
      });
  });
});

// ----------------- Profile -----------------
document.addEventListener("DOMContentLoaded", function () {
  const access_token = localStorage.getItem("access_token");

  $("#login-from").hide();
  $(".profile-container").hide();

  if (access_token) {
    fetchUserProfile(access_token);
  } else {
    $("#login-form").show();
  }
});

function fetchUserProfile(access_token) {
  fetch(`${baseUrl}/api/1.0/user/profile`, {
    method: "GET",
    headers: {
      Authorization: "Bearer " + access_token,
    },
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error("Failed to get user profile");
      } else {
        return response.json();
      }
    })
    .then((data) => {
      $("#login-form").hide();
      displayUserProfile(data.data);
    })
    .catch((error) => {
      console.error(error);
      $("#login-form").show();
    });
}

function displayUserProfile(profileData) {
  if ($(".profile-container")) {
    $(".profile-container").html(`
        <div class="profile">
          <div class="profile-header">
            <img src="../images/member.png" alt="${profileData.name}" class="profile-picture">
            <h2>歡迎回來，<span class="user-name">${profileData.name}</span></h2>
          </div>
          <div class="profile-info">
            <p><strong>電子郵件：</strong> <span class="user-email">${profileData.email}</span></p>
          </div>
        </div>
      `);
    $(".profile-container").show();
  } else {
    console.log("profile-container not found");
  }
}

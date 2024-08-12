console.log("Script started");

window.fbAsyncInit = function () {
    FB.init({
        appId: '3553002434994936',
        cookie: true,               // Enable cookies to allow the server to access the session.
        xfbml: true,                // Parse social plugins on this webpage.
        version: 'v20.0'            // Use this Graph API version for this call.
    });
};

function checkLoginState() {
    FB.getLoginStatus(function (response) {
        statusChangeCallback(response);
    });
}

function statusChangeCallback(response) {
    if (response.status === 'connected') {
        const accessToken = response.authResponse.accessToken;
        document.getElementById('accesstoken').innerHTML = accessToken;
        testAPI();
        sendTokenToBackend(accessToken);
    } else {
        FB.login(function (response) {
                document.getElementById('accesstoken').innerHTML = response.authResponse.accessToken;
                sendTokenToBackend(response.authResponse.accessToken);
            }
        );
    }
}

function testAPI() {
    console.log('Welcome! Fetching your information.... ');
    FB.api(
        '/me',
        'GET',
        {"fields": "id,name,picture{url},email"},
        function (response) {
            console.log('Successful login for: ' + response.name);
        })
}

function sendTokenToBackend(accessToken) {
    // Use the fetch API to send a POST request to backend
    fetch('https://localhost:8080/api/1.0/user/signin', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            provider: "facebook",
            access_token: accessToken
        })
    })
        .then(response => response.json())
        .then(data => {
            console.log('Success:', data);
            document.getElementById('jwt').innerHTML = data.data.access_token;
            // The data returned by the backend is processed here, such as saving the JWT token
            localStorage.setItem('jwt', data.data.access_token);

        })
        .catch((error) => {
            console.error('Error:', error);
        });
}

// document.addEventListener('DOMContentLoaded', function () {
//     const fetchProfileBtn = document.getElementById('fetchProfileBtn');
//     if (fetchProfileBtn) {
//         fetchProfileBtn.addEventListener('click', fetchProfile);
//     }
// });

function fetchProfile() {
    const jwt = localStorage.getItem('jwt');
    if (jwt) {
        fetch('https://localhost:8080/api/1.0/user/profile', {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${jwt}`,
                'Content-Type': 'application/json'
            }
        })
            .then(response => response.json())
            .then(data => {
                const profile = jsonToHtml(data.data);
                alert('Get profile successfully!');
                document.getElementById('profile').innerHTML = profile;
            })
            .catch((error) => {
                console.error('Error:', error);
            });
    }
}

function jsonToHtml(json) {
    if (typeof json !== 'object' || json === null) {
        return JSON.stringify(json);
    }

    let html = '<ul>';
    for (let key in json) {
        html += '<li>';
        html += `<strong>${key}:</strong> `;
        if (typeof json[key] === 'object' && json[key] !== null) {
            html += jsonToHtml(json[key]);
        } else {
            html += json[key];
        }
        html += '</li>';
    }
    html += '</ul>';
    return html;
}



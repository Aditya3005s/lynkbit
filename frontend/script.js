
const form = document.getElementById("urlForm");
const originalUrlInput = document.getElementById("originalUrl");
const result = document.getElementById("result");
const shortenButton = form.querySelector('button[type="submit"]');

const API_URL = "https://lynkbit-api.onrender.com/api/urls";

form.addEventListener("submit", async function (event) {
    event.preventDefault();

    const originalUrl = originalUrlInput.value.trim();

    if (!originalUrl) {
        result.textContent = "Please enter a URL first.";
        originalUrlInput.focus();
        return;
    }

    shortenButton.disabled = true;
    shortenButton.textContent = "Shortening...";
    result.textContent = "Creating your LynkBit link...";

    try {
        const response = await fetch(API_URL, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                originalUrl: originalUrl
            })
        });

        // Read the response safely, even if the server returns
        // an empty or non-JSON error response.
        const responseText = await response.text();
        let data = {};

        if (responseText) {
            try {
                data = JSON.parse(responseText);
            } catch {
                throw new Error(
                    "The server returned an unexpected response."
                );
            }
        }

        if (!response.ok) {
            throw new Error(
                data.message ||
                data.error ||
                `Unable to shorten URL (${response.status}).`
            );
        }

        if (!data.shortUrl) {
            throw new Error("The server did not return a short URL.");
        }

        // Build the result safely without inserting HTML strings.
        result.replaceChildren();

        const message = document.createElement("p");
        message.textContent = "Your short URL is ready!";

        const link = document.createElement("a");
        link.href = data.shortUrl;
        link.textContent = data.shortUrl;
        link.target = "_blank";
        link.rel = "noopener noreferrer";

        const copyButton = document.createElement("button");
        copyButton.type = "button";
        copyButton.textContent = "Copy Link";

        copyButton.addEventListener("click", async function () {
            try {
                await navigator.clipboard.writeText(data.shortUrl);
                copyButton.textContent = "Copied!";

                setTimeout(() => {
                    copyButton.textContent = "Copy Link";
                }, 2000);
            } catch {
                copyButton.textContent = "Copy manually";
                link.focus();
            }
        });

        result.append(message, link, copyButton);

    } catch (error) {
        result.textContent =
            error.message || "Unable to connect to the server.";

    } finally {
        shortenButton.disabled = false;
        shortenButton.textContent = "Shorten URL";
    }
});

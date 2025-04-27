<#-- Ensure keycloakLogoutUrl has a fallback value if it's missing -->
<#assign keycloakLogoutUrl = keycloakLogoutUrl!"/default/logout/url">

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>Logging out...</title>
</head>
<body>
    <noscript>
        <p>Logout completed. You can close this window.</p>
    </noscript>

    <#-- If keycloakLogoutUrl exists, redirect to it, else use the base URL of the current page -->
    <#if keycloakLogoutUrl??>
        <script>
            // Redirecting to the Keycloak logout URL
            window.location.href = "${keycloakLogoutUrl}";
        </script>
    <#else>
        <#-- Get the current base URL dynamically using JavaScript -->
        <script>
            // Use the current base URL dynamically
            var baseUrl = window.location.origin;  // This gets the base URL, e.g., http://localhost:9000
            window.location.href = baseUrl;  // Redirect to base URL
        </script>
    </#if>
</body>
</html>

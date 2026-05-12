window.onload = function () {
  //<editor-fold desc="Changeable Configuration Block">
  function getQueryParam(name) {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get(name);
  }

  const service = getQueryParam('q')
  const path = `${(service ? `/${service}` : '')}`
  window.ui = SwaggerUIBundle({
    url: `${path}/v3/api-docs`,
    dom_id: '#swagger-ui',
    deepLinking: true,
    presets: [
      SwaggerUIBundle.presets.apis,
      SwaggerUIStandalonePreset
    ],
    plugins: [
      SwaggerUIBundle.plugins.DownloadUrl
    ],
    layout: "StandaloneLayout",
    requestInterceptor: (req) => {
      const service = new URLSearchParams(window.location.search).get('q');
      if (service && !req.url.includes(`/${service}/`)) {
        // If the URL contains a container ID or different host, rewrite it to use the Gateway
        const baseUrl = `${window.location.protocol}//${window.location.host}`;
        if (req.url.includes(':8090')) {
          req.url = req.url.replace(/^http:\/\/.*:\d+/, `${baseUrl}/${service}`);
        }
      }
      return req;
    },
    onComplete: function () {
      const baseUrl = `${window.location.protocol}//${window.location.host}`;
      const currentSpec = window.ui.getSystem().specSelectors.specJson();
      const modifiedServers = {
        ...JSON.parse(JSON.stringify(currentSpec)),
        servers: [
          {
            url: `${baseUrl}${path}`,
            description: currentSpec.get('servers') ?
              currentSpec.get('servers').get(0).get('description') : ""
          },
        ],
      };
      window.ui.specActions.updateJsonSpec(modifiedServers);
      window.ui.initOAuth({});
    }
  });

  //</editor-fold>
};

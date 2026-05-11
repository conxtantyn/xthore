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

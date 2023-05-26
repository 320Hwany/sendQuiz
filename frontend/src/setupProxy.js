const { createProxyMiddleware } = require('http-proxy-middleware')

module.exports = function (app) {
    app.use(
        "/api",
        createProxyMiddleware({
            target: 'http://54.180.188.98:8080',
            changeOrigin: true,
        })
    );
};
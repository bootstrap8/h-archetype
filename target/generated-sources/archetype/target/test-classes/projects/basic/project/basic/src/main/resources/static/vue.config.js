const {defineConfig} = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  outputDir: 'demo-ui',
  publicPath: './',
  devServer: {
    proxy: {
      '/dev': {
        target: `http://localhost:8080`,
        changeOrigin: true,
        secure: false,
        pathRewrite: { '^/dev': '' },
      },
    },
  }
})

const {defineConfig} = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  outputDir: 'ui-demo',
  publicPath: './',
  devServer: {
    proxy: {
      '/dev': {
        target: 'http://localhost:30150',
        changeOrigin: true,
        secure: false,
        pathRewrite: { '^/dev': '' },
      },
    },
  }
})

const {defineConfig} = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  outputDir: '${module}-ui',
  publicPath: './',
  devServer: {
    proxy: {
      '/dev': {
        target: `http://localhost:${appPort}`,
        changeOrigin: true,
        secure: false,
        pathRewrite: { '^/dev': '' },
      },
    },
  }
})

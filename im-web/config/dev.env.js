'use strict'
const merge = require('webpack-merge')
const prodEnv = require('./prod.env')

module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
  // BASE_API: '"http://www.dzry.top:2006"',
  // WEBSOCKET_URL: '"ws://www.dzry.top:2007"'

  BASE_API: '"http://localhost:8001"',
  WEBSOCKET_URL: '"ws://localhost:8002"'
})

module.exports = {
    resolve: {
        alias: {
            'vue$': 'vue/dist/vue.esm.js'
        },
        extensions: ['*', '.js', '.vue', '.json']
    },
    module: {
        rules: [
            {
                test: /\.css$/,
                use: ['style-loader', 'css-loader']
            },
            {
                test: /\.(woff(2)?|ttf|eot|svg)(\?v=\d+\.\d+\.\d+)?$/,
                use: [{
                    loader: 'file-loader',
                    options: {
                        name: '[name].[ext]',
                        outputPath: 'fonts/'
                    }
                }]
            },
            {
                test: /\.vue$/,
                loader: 'vue-loader'
            }
        ]
    },
    entry: {
        app: ["./src/main/webapp/js/index.js"]
    },
    output: {
        filename: "bundle.js"
    },
    devServer: {
        port: 8080,
        proxy: {
            '/service/todo': {
                target: 'http://localhost:12111',
                ws: true,
                changeOrigin: true
            }
        }
    }
}
var HtmlWebpackPlugin = require('html-webpack-plugin');

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
                test: /\.vue$/,
                loader: 'vue-loader'
            },
            {
                test: /\.(jpg|png)$/,
                use: {
                    loader: "file-loader",
                    options: {
                        name: "[path][name].[hash].[ext]"
                    }
                }
            },
            {
                test: /\.(woff(2)?|ttf|eot|svg)(\?v=\d+\.\d+\.\d+)?$/,
                use: {
                    loader: 'file-loader',
                    options: {
                        name: '[name].[ext]',
                        outputPath: 'fonts/'
                    }
                }
            }
        ]
    },
    plugins: [
        new HtmlWebpackPlugin({
            filename: './index.html',
            template: './src/main/webapp/index.html'
        })
    ],
    entry: {
        app: ["./src/main/webapp/js/index.js"]
    },
    output: {
        filename: '[name].[contenthash].js'
    },
    devServer: {
        port: 8080,
        proxy: {
            '/service': {
                target: 'http://localhost:12111',
                ws: true,
                changeOrigin: true,
                secure: false
            }
        }
    }
}
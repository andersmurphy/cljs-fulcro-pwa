## Dependencies

Install dependencies:

`yarn install`

## Repl

Start a repl:

`yarn shadow-cljs clj-repl`

Run the watch command:

`(shadow/watch :app)`

Open your browser and got to:

`http://localhost:8000/`

Switch to cljs repl:

`(shadow/repl :app)`

Eval some code:

`(js/alert "foo")`

If get this message after evaluating some code:

`There is no connected JS runtime.`

You need to open your App in the browser at:

`http://localhost:8000/`

You can quit the cljs repl and return to the shadow repl with:

`:repl/quit`

## Service Worker

Remember the service worker adds a layer of caching so things like the index and static assets won't refresh on reload. You can bypass it in development by using chrome devtools:

Application -> Service Workers -> Bypass for network

Just remember to turn it back on when you want to test the service worker behaviour.

You can also simulate being offline

Application -> Service Workers -> Offline

## Deploy

Production build from the shadow repl:

`(shadow/release :app)`

Push changes and the project will deploy automatically to:

https://andersmurphy.github.io/cljs-fulcro-pwa/

## Documentation

[shadow-cljs](https://shadow-cljs.github.io/docs/UsersGuide.html)

[fulcro](https://book.fulcrologic.com/)

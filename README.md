## Dependencies

Install dependencies:

`yarn install`

## Repl

Start a repl:

`yarn shadow-cljs clj-repl`

Run the watch command:

`(shadow/watch :main)`

Open your browser and got to:

`http://localhost:8000/`

Switch to cljs repl:

`(shadow/repl :main)`

Eval some code:

`(js/alert "foo")`

If get this message after evaluating some code:

`There is no connected JS runtime.`

You need to open your App in the browser at:

`http://localhost:8000/`

You can quit the cljs repl and return to the shadow repl with:

`:repl/quit`

## Deploy

Production build:

`yarn shadow-cljs release main`

Or from the shadow repl:

`(shadow/release :main)`

Push changes and the project will deploy automatically to:

https://andersmurphy.github.io/cljs-fulcro-pwa/

## Documentation

[shadow-cljs](https://shadow-cljs.github.io/docs/UsersGuide.html)

[fulcro](https://book.fulcrologic.com/)

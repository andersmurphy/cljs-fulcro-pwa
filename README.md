## Dependencies

Install dependencies:

`yarn install`

# Repl

Start a repl:

`yarn shadow-cljs clj-repl`

Run watch command:

`(shadow/watch :main)`

Open `http://localhost:8000/` in your browser:

Switch to cljs repl:

`(shadow/repl :main)`

Eval some code:

`(js/alert "foo")`

If you run into this:

`There is no connected JS runtime.`

You need to open your App in the Browser at `http://localhost:8000/`.

You can quit the cljs repl and return to the shadow repl with:

`:repl/quit`

## Deploy

Production build:

`yarn shadow-cljs release main`

Or from the shadow repl:

`(shadow/release :main)`

Push changes and the project will deploy automatically to:

https://andersmurphy.github.io/cljs-fulcro-pwa/

# reagent-todolist

A Reagent todo list application for a demo.

## Requirements

- Clojure
- Leiningen

## Setup

```sh
lein deps
```

## Development

To start a server & REPL run:

```sh
lein figwheel
```

This will start a server on port `3449`.

### CIDER

To start the server & a CIDER REPL from within Emacs, open one of the project's
`cljs` files in a buffer then run:

```emacs
cider-jack-in-cljs
```

This will also start a server on port `3449`.

Note that you can use either `lein figwheel` or `cider-jack-in-cljs` but not
both.

## Distribution

```sh
lein package
```

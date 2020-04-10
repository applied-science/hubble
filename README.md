# hubble

A thin wrapper around Darkstar and Batik to allow easy viewing of Vega
and Vega-lite graphs from the Clojure REPL.

## Installation

We have not yet released to Clojars, so we recommended you use deps.edn:

``` clojure
applied-science/hubble {:git/url "https://github.com/appliedsciencestudio/hubble/"
                        :sha "50db98e70344905777c5df9e98a6b029cfdc8481"}
```

## Usage

``` clojure
(ns test
  (:require [applied-science.hubble :refer [plot-vega-lite! keep-on-top!]]))

;; pops up a window containing this visualization
(plot-vega-lite!
   {:data {:values (map hash-map
                        (repeat :a)
                        (range 1 1000)
                        (repeat :b)
                        (repeatedly #(+ 25 (* 50 (Math/random)))))}
    :mark "line",
    :width 800
    :height 600
    :encoding {:x {:field :a, :type "ordinal", :axis {"labelAngle" 0}},
               :y {:field :b, :type "quantitative"}}})

;; tells hubble to float on top of other windows, especially useful if
;; one is running a full screen editor but they still want to see the
;; current plot
(keep-on-top!)
```

## TODO

* Add Batik's PNG transcoder support to export PNGs of drawings.

## Development 

Build a deployable jar of this library:

    $ clojure -A:jar

Install it locally:

    $ clojure -A:install

Deploy it to Clojars -- needs `CLOJARS_USERNAME` and `CLOJARS_PASSWORD` environment variables:

    $ clojure -A:deploy

## License

Copyright © 2020 Jack

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.

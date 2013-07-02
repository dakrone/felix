# felix

Felix is a silly, tiny library used to monitor for when things get
garbage collected, to make sure you aren't holding onto the head of
large sequences, or large objects in general.

It's named after our really nice garbage man, who leaves us a card
every Christmas, and always waits for me if I'm late taking the bin to
the curb.

## Usage

In your dependencies:

```clojure
[felix "1.0.0"]
```

It's easy to use, there's only one real method, `monitor`:

```clojure
(ns my.ns
  (:require [felix.core :refer [monitor force-gc]]))

;; monitor returns the seq, so I wrap it in a do so the giant seq
;; doesn't print out at a REPL
(do
  (monitor (range 10000000000)
           (fn []
             (prn "The sequence has been cleared")))
  nil)

;; Manually force garbage collection
(force-gc)
;; => "The sequence has been cleared"
```

See the doc-string on monitor for full usage.

## License

EPL licensed.

Copyright © 2013 Lee Hinman

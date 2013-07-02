(ns felix.test.core
  (:require [clojure.test :refer :all]
            [felix.core :refer :all]))

(deftest monitor-handlers
  (let [p (promise)]
    (monitor (range 10000000000)
             (fn []
               (deliver p :cleared)))
    (force-gc)
    (is (= :cleared (deref p 3000 :not-cleared)))))

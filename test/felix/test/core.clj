(ns felix.test.core
  (:require [clojure.test :refer :all]
            [felix.core :refer :all]))

(deftest monitor-handlers
  (let [p (promise)
        r (range 10000000000)]
    (monitor r
             (fn []
               (deliver p :cleared)))
    (force-gc)
    (is (= :cleared (deref p 3000 :not-cleared)))))

(deftest monitor-handlers-failure
  (let [p (promise)
        r (range 10000000000)]
    (monitor r
             (fn []
               (deliver p :cleared)))
    (force-gc)
    (is (= :not-cleared (deref p 3000 :not-cleared)))
    (is r)))

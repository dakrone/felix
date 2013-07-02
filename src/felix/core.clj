(ns felix.core
  (:import (java.lang.ref WeakReference)
           (java.lang.management ManagementFactory)))

(defn monitor
  "Monitor the thing for when it is garbage collected, calling the handler
  every 1000ms to check whether it has been. A custom poll-time can be
  specified also. Returns the object being monitored."
  [thing handler-fn & [poll-time]]
  {:pre [(not (nil? thing))]}
  (let [wr (WeakReference. thing)]
    (future
      (loop []
        (if (nil? (.get wr))
          (handler-fn)
          (do
            (Thread/sleep (or poll-time 1000))
            (recur)))))
    thing))

;; Taken from Criterium for testing, credit to hugod
(def ^:dynamic *max-gc-attempts* 100)

(defn heap-used
  "Report a (inconsistent) snapshot of the heap memory used."
  []
  (let [runtime (Runtime/getRuntime)]
    (- (.totalMemory runtime) (.freeMemory runtime))))

(defn force-gc
  "Force garbage collection and finalisers so that execution time associated
   with this is not incurred later. Up to max-attempts are made."
  ([] (force-gc *max-gc-attempts*))
  ([max-attempts]
     (loop [memory-used (heap-used)
            attempts 0]
       (System/runFinalization)
       (System/gc)
       (let [new-memory-used (heap-used)]
         (if (and (or (pos? (.. ManagementFactory
                                getMemoryMXBean
                                getObjectPendingFinalizationCount))
                      (> memory-used new-memory-used))
                  (< attempts max-attempts))
           (recur new-memory-used (inc attempts)))))))

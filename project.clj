(defproject felix "1.0.0"
  :description "Monitor your things for garbage collection."
  :url "http://github.com/dakrone/felix"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :profiles {:dev {:dependencies [[org.clojure/clojure "1.5.1"]]}}
  :min-lein-version "2.0.0"
  :plugins [[lein-bikeshed "0.1.2"]]
  :jvm-opts ["-Xmx256m"])

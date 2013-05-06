(defproject fui "0.1.0-SNAPSHOT"
  :description "Functional reactive Clojure wrapper for Swing GUI development"
  :url "http://github.com/avh4/fui"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [lamina "0.5.0-beta9"]]
  :profiles {
    :dev {
      :plugins [[lein-midje "3.0.0"]]
      :dependencies [[midje "1.5.0"]] }} )

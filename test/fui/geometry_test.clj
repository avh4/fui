(ns fui.geometry-test
  (:use [fui.geometry])
  (:use [midje.sweet]))

(facts "about manipulating rectangles"
  (facts "about laying out vertical rectangles"
    (fact ""
      (rect-vertical 0 [0 0 800 600] 30 5) => [5 5 790 20]
      (rect-vertical 1 [0 0 800 600] 30 5) => [5 35 790 20]
      (rect-vertical 0 [80 80 100 100] 30 5) => [85 85 90 20] )))

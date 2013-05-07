(ns fui.geometry
  (:require [clojure.math.numeric-tower :as math]) )

(defn from-center
  ([x y w h] [(- x (/ w 2)) (- y (/ h 2)) w h])
  ([x y size] (from-center x y size size)) )

(defn rect-inset [inset [x y w h]]
  [(+ x inset) (+ y inset) (- w inset inset) (- h inset inset)])

(defn rect-divide [[x y w h] left top right bottom]
  [(+ x (* w left))
   (+ y (* h top))
   (* w (- right left))
   (* h (- bottom top)) ])

(defn rect-grid [i n bounds inset]
  (let [area (rect-inset (/ inset 2) bounds)
        cols ((math/exact-integer-sqrt n) 0)
        rows (math/ceil (/ n cols))
        width-percent (/ 1 cols)
        height-percent (/ 1 rows)
        row (math/floor (/ i cols))
        col (mod i cols)]
    (rect-inset (/ inset 2) (rect-divide area (* col width-percent) (* row height-percent) 
      (* (inc col) width-percent) (* (inc row) height-percent)))
  ))

(defn rect-vertical [i [x y w h] height inset]
  [(+ x inset) (+ y inset (* i height)) (- w inset inset) (- height inset inset)])
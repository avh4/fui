(ns fui.drawing)
(import '(java.awt Color))

(def color-rgb (memoize (fn [r g b] (new Color r g b))))

(defn fill-rect [bounds color]
  {:shape :rect, :bounds bounds, :color color})

(defn fill-oval [bounds color]
  {:shape :oval, :bounds bounds, :color color})

(defn draw-text
  ([text left-x baseline-y] (draw-text text left-x baseline-y (color-rgb 0 0 0) "Lucida Grande" 13))
  ([text left-x baseline-y color font font-size] 
    {:shape :text, :text text 
                          :font font :font-size font-size 
                          :left-x left-x :baseline-y baseline-y
                          :color color}) )
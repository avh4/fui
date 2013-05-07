(ns fui.core
  (:use [lamina.core]))
(import '(javax.swing JFrame JComponent SwingUtilities WindowConstants))
(import '(java.awt Dimension Graphics Color Font RenderingHints))
(import '(java.awt.event MouseListener MouseMotionListener))

(defmulti draw :shape)
(defmethod draw :rect [{color :color [x y w h] :bounds} g]
  (.setColor g color)
  (.fillRect g x y w h))
(defmethod draw :oval [{color :color [x y w h] :bounds} g]
  (.setColor g color)
  (.fillOval g x y w h))
(def load-font (memoize (fn [font font-size] (new Font font 0 font-size))))
(defmethod draw :text [{:keys [text font font-size color left-x baseline-y]} g]
  (.setColor g color)
  (.setFont g (load-font font font-size))
  (.drawString g text left-x baseline-y))

(defmacro with-action [component & body]
  `(.addActionListener ~component
     (proxy [java.awt.event.ActionListener] []
       (actionPerformed [~'event] ~@body))))

(defn component [width height graphics-signal input-channel]
  (let [graphics-ref (ref [])
        self (proxy [JComponent MouseListener MouseMotionListener] []
          (getPreferredSize [] (new Dimension width height))
          (paintComponent [g]
            (.setRenderingHint g
                    RenderingHints/KEY_TEXT_ANTIALIASING
                    RenderingHints/VALUE_TEXT_ANTIALIAS_ON)
            (doseq [command @graphics-ref]
              (draw command g)))
          (mouseClicked [e]
            (enqueue input-channel [:click (.getX e) (.getY e)]))
          (mouseEntered [e])
          (mouseExited [e])
          (mousePressed [e])
          (mouseReleased [e])
          (mouseMoved [e]
            (enqueue input-channel [:move (.getX e) (.getY e)]))
          (mouseDragged [e])
          ) ]
    (.addMouseListener self self)
    (.addMouseMotionListener self self)
    (receive-all graphics-signal
      (fn [x] (do
        (dosync (ref-set graphics-ref x))
        (SwingUtilities/invokeLater #(.repaint self)))))
    self))

(defn window [component title-signal]
  (let [self (new JFrame)]
    (receive-all title-signal
      (fn [title]
        (SwingUtilities/invokeLater #(.setTitle self title))))
    (doto self
      (.add component)
      (.setDefaultCloseOperation WindowConstants/EXIT_ON_CLOSE)) ))

(defn show [window]
  (SwingUtilities/invokeLater
    #(doto window
      (.pack)
      (.setLocationRelativeTo nil)
      (.setVisible true))))

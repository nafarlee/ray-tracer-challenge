(ns a.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(defrecord Tuple [x y z w])

(defn point [x y z]
  (->Tuple x y z 1))

(defn point? [x]
  (and (instance? Tuple x)
       (= 1.0 (:w x))))

(defn vector' [x y z]
  (->Tuple x y z 0))

(defn vector'? [x]
  (and (instance? Tuple x)
       (zero? (:w x))))

(defn add [t1 t2]
  (->Tuple (+ (:x t1) (:x t2))
           (+ (:y t1) (:y t2))
           (+ (:z t1) (:z t2))
           (+ (:w t1) (:w t2))))

(defn subtract
  [{ax :x ay :y az :z aw :w} {bx :x by :y bz :z bw :w}]
  (->Tuple (- ax bx)
           (- ay by)
           (- az bz)
           (- aw bw)))

(defn negate [t]
  (subtract (vector' 0 0 0) t))

(defn product
  [{x :x y :y z :z w :w} i]
  (->Tuple (* x i)
           (* y i)
           (* z i)
           (* w i)))

(defn divide
  [{x :x y :y z :z w :w} i]
  (->Tuple (/ x i)
           (/ y i)
           (/ z i)
           (/ w i)))

(defn magnitude [v]
  (Math/sqrt (+ (Math/pow (:x v) 2)
                (Math/pow (:y v) 2)
                (Math/pow (:z v) 2))))

(defn normalize
  [{x :x y :y z :z :as v}]
  (let [m (magnitude v)]
    (vector' (/ x m)
             (/ y m)
             (/ z m))))

(defn dot
  [{ax :x ay :y az :z aw :w} {bx :x by :y bz :z bw :w}]
  (+ (* ax bx)
     (* ay by)
     (* az bz)
     (* aw bw)))

(defn cross
  [{ax :x ay :y az :z} {bx :x by :y bz :z}]
  (vector' (- (* ay bz)
              (* az by))
           (- (* az bx)
              (* ax bz))
           (- (* ax by)
              (* ay bx))))

(ns a.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(defrecord Tuple [x y z w])

(defn point [x y z]
  (->Tuple x y z 1))

(defn point? [{w :w :as t}]
  (and (instance? Tuple t)
       (= 1.0 w)))

(defn vector' [x y z]
  (->Tuple x y z 0))

(defn vector'? [{w :w :as t}]
  (and (instance? Tuple t)
       (zero? w)))

(defn- across
  [f {ax :x ay :y az :z aw :w} {bx :x by :y bz :z bw :w}]
  (->Tuple (f ax bx)
           (f ay by)
           (f az bz)
           (f aw bw)))

(def add (partial across +))

(def subtract (partial across -))

(def negate (partial subtract (vector' 0 0 0)))

(defn element-wise
  [f {x :x y :y z :z w :w} i]
  (->Tuple (f x i)
           (f y i)
           (f z i)
           (f w i)))

(def product (partial element-wise *))

(def divide (partial element-wise /))

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

(defn dot [a b]
  (->> (across * a b)
       vals
       (apply +)))

(defn cross
  [{ax :x ay :y az :z} {bx :x by :y bz :z}]
  (vector' (- (* ay bz)
              (* az by))
           (- (* az bx)
              (* ax bz))
           (- (* ax by)
              (* ay bx))))

(defrecord Projectile [position velocity])

(defrecord Environment [gravity wind])

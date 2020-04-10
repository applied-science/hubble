(ns applied-science.hubble
  (:require [seesaw.core :refer :all]
            [jsonista.core :as json]
            [applied-science.darkstar :as ds]))

;; TODO factor out Seesaw, which we are hardly using.

(native!)

(def svg-canvas
  (org.apache.batik.swing.JSVGCanvas.))

(defonce the-frame
  (let [f (-> (frame :title "Hubble"
                     :content (scrollable svg-canvas))
              pack!)]
    ;; enables fullscreen, which also enables split screen
    (when (.startsWith (System/getProperty "os.name") "Mac OS X")
      (try
        (com.apple.eawt.FullScreenUtilities/setWindowCanFullScreen f true)  
        (catch Exception e (println e))))
    f))

(defn show-svg! [svg]
  (.setSVGDocument svg-canvas
                   (.createDocument (org.apache.batik.anim.dom.SAXSVGDocumentFactory.
                                     (org.apache.batik.util.XMLResourceDescriptor/getXMLParserClassName))
                                    "" ;; TODO take path arg
                                    (java.io.StringReader. svg)))
  (show! the-frame))

(defn keep-on-top! []
  (doto the-frame
    (.setVisible true)
    (.toFront)
    (.repaint) 
    (.setAlwaysOnTop true)))

(defn plot-vega! [spec]
  (show-svg! (ds/vega-spec->svg (json/write-value-as-string spec))))

(defn plot-vega-lite! [spec]
  (show-svg! (ds/vega-lite-spec->svg (json/write-value-as-string spec))))

(comment

  (show-svg! (ds/vega-lite-spec->svg (slurp "/Users/jack/src/darkstar/vega-lite-example.json")))

  (keep-on-top!)

  (plot-vega-lite!
   {:data {:values (map hash-map
                        (repeat :a)
                        (range 1 1000)
                        (repeat :b)
                        (repeatedly #(+ 25 (* 50 (Math/random)))))}
    :mark "line",
    :width 800
    :height 600
    :encoding {:x {:field :a, :type "ordinal", :axis {"labelAngle" 0}},
               :y {:field :b, :type "quantitative"}}})

  )

;;(.requestToggleFullScreen (com.apple.eawt.Application/getApplication) @the-frame)

;; TODO add PNG decoder option
;;org.apache.batik.transcoder.image.PNGTranscoder
;; TranscoderInput input = new TranscoderInput(document);
;;        OutputStream ostream = new FileOutputStream("out.jpg");
;;        TranscoderOutput output = new TranscoderOutput(ostream);

;;        // Perform the transcoding.
;;        t.transcode(input, output);
;;        ostream.flush();
;;        ostream.close();

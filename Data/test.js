/*
var map;
var service;
var infowindow;

function initMap() {
  var sydney = new google.maps.LatLng(-33.867, 151.195);

  infowindow = new google.maps.InfoWindow();

  map = new google.maps.Map(
      document.getElementById('map'), {center: sydney, zoom: 15});

  var request = {
    query: 'Museum of Contemporary Art Australia',
    fields: ['name', 'geometry'],
  };

  var service = new google.maps.places.PlacesService(map);

  service.findPlaceFromQuery(request, function(results, status) {
    if (status === google.maps.places.PlacesServiceStatus.OK) {
      for (var i = 0; i < results.length; i++) {
        createMarker(results[i]);
      }
      map.setCenter(results[0].geometry.location);
    }
  });
}

initMap();
*/

var data = {
  "html_attributions" : [],
  "next_page_token" : "Aap_uEAYsiIG3EMmcCAuX5cDGWTTr_LIcGPeFJoHbgJDS1JKfzloHEqR0Fqdy8rSMSETxgairTtTroVJEreSWKSfwdt3mJaojAnecDXFR9Ol2S8bqaHFRveWk55Zo_UTcCJfWSuq7vBPsIDRhZ32BCQRciej4BBSwGYzpIx3eUJeGE0KnsSEbcRi03lfxIGfb1DOn3DfoUq3TXipvnQV3e1lJbikU0xLXAyC4k-fp8G5hqqzTEtChGOcklHF4xBxt1YejEtK56dNOO_zBlENqMdrf0OWpFR1vjwFwfbK4cUQL_pECt4mNZZkJckAckylwU8qHTbIZ5hgEhI6UTvqvrzC-TDEJcvDENKftRpZojDGlsatKfu-wK53HdTRXF9gTmxqGcuNONKMqZKd2vTmj_I5DJIqVX62_SWFmBctgYFIHoHA",
  "results" : [
     {
        "geometry" : {
           "location" : {
              "lat" : 48.936181,
              "lng" : 2.357443
           },
           "viewport" : {
              "northeast" : {
                 "lat" : 48.9521127750054,
                 "lng" : 2.398158968608624
              },
              "southwest" : {
                 "lat" : 48.90148480251605,
                 "lng" : 2.333246172432761
              }
           }
        },
        "icon" : "https://maps.gstatic.com/mapfiles/place_api/icons/v1/png_71/geocode-71.png",
        "icon_background_color" : "#7B9EB0",
        "icon_mask_base_uri" : "https://maps.gstatic.com/mapfiles/place_api/icons/v2/generic_pinlet",
        "name" : "Saint-Denis",
        "photos" : [
           {
              "height" : 3000,
              "html_attributions" : [
                 "\u003ca href=\"https://maps.google.com/maps/contrib/118083489632981261344\"\u003eEmil Tamas\u003c/a\u003e"
              ],
              "photo_reference" : "Aap_uECiXcTB4Fi2agGsoXAiqeW9IG29Xb-GNesj9oLz-nkzl6JKozmnkfjvo2a-AHKkvbtJhx0d_69yyY0EbXbVjqMKGWsI720xnd-G8FrXEb2uW-QwST1xVD4ivmhl92qyn2Km6H7SQAgN0y09fKRpDX98xNvrJbQ-6kh3yrgnb6EDk9o",
              "width" : 3000
           }
        ],
        "place_id" : "ChIJYW0056pu5kcRDeko-brr78c",
        "reference" : "ChIJYW0056pu5kcRDeko-brr78c",
        "scope" : "GOOGLE",
        "types" : [ "locality", "political" ],
        "vicinity" : "Saint-Denis"
     },
     {
        "business_status" : "OPERATIONAL",
        "geometry" : {
           "location" : {
              "lat" : 48.9306271,
              "lng" : 2.3566758
           },
           "viewport" : {
              "northeast" : {
                 "lat" : 48.93198638029149,
                 "lng" : 2.357972580291502
              },
              "southwest" : {
                 "lat" : 48.9292884197085,
                 "lng" : 2.355274619708498
              }
           }
        },
        "icon" : "https://maps.gstatic.com/mapfiles/place_api/icons/v1/png_71/lodging-71.png",
        "icon_background_color" : "#909CE1",
        "icon_mask_base_uri" : "https://maps.gstatic.com/mapfiles/place_api/icons/v2/hotel_pinlet",
        "name" : "HOTEL MODERNE",
        "photos" : [
           {
              "height" : 1192,
              "html_attributions" : [
                 "\u003ca href=\"https://maps.google.com/maps/contrib/103229731147405923407\"\u003eHOTEL MODERNE\u003c/a\u003e"
              ],
              "photo_reference" : "Aap_uEAfNKA6CrPllDXUEuTwEgXNUT6pf7d9ClXKev0pef3UvU2gl20Dsthfkmj5S_yNTuNIa_YAqOb5l41nS-9Zz3K6lm3Olr_PW2qRsAvw9k2GIfZGYUTUS5fgyzDNriRMwsI0IFgE-L-NYGIdP0iLktqmDRd-DWevE1JZx2FFJZjxfPUr",
              "width" : 2120
           }
        ],
        "place_id" : "ChIJPbewTrFu5kcRw3qGBgSLoQI",
        "plus_code" : {
           "compound_code" : "W9J4+7M Saint-Denis, France",
           "global_code" : "8FW4W9J4+7M"
        },
        "rating" : 3.2,
        "reference" : "ChIJPbewTrFu5kcRw3qGBgSLoQI",
        "scope" : "GOOGLE",
        "types" : [ "lodging", "point_of_interest", "establishment" ],
        "user_ratings_total" : 87,
        "vicinity" : "4 Bis Rue Gabriel Péri, Saint-Denis"
     },
     {
        "business_status" : "OPERATIONAL",
        "geometry" : {
           "location" : {
              "lat" : 48.9349683,
              "lng" : 2.3498459
           },
           "viewport" : {
              "northeast" : {
                 "lat" : 48.9363204302915,
                 "lng" : 2.351338780291502
              },
              "southwest" : {
                 "lat" : 48.9336224697085,
                 "lng" : 2.348640819708498
              }
           }
        },
        "icon" : "https://maps.gstatic.com/mapfiles/place_api/icons/v1/png_71/lodging-71.png",
        "icon_background_color" : "#909CE1",
        "icon_mask_base_uri" : "https://maps.gstatic.com/mapfiles/place_api/icons/v2/hotel_pinlet",
        "name" : "Maison des 5 Silences",
        "place_id" : "ChIJw9fNk8pu5kcRI2bUKX56xDY",
        "plus_code" : {
           "compound_code" : "W8MX+XW Saint-Denis, France",
           "global_code" : "8FW4W8MX+XW"
        },
        "rating" : 2.9,
        "reference" : "ChIJw9fNk8pu5kcRI2bUKX56xDY",
        "scope" : "GOOGLE",
        "types" : [ "lodging", "point_of_interest", "establishment" ],
        "user_ratings_total" : 19,
        "vicinity" : "17 Boulevard Jules Guesde, Saint-Denis"
     },
     {
        "business_status" : "OPERATIONAL",
        "geometry" : {
           "location" : {
              "lat" : 48.93546120000001,
              "lng" : 2.3598354
           },
           "viewport" : {
              "northeast" : {
                 "lat" : 48.9370818302915,
                 "lng" : 2.360811549999999
              },
              "southwest" : {
                 "lat" : 48.9343838697085,
                 "lng" : 2.35690695
              }
           }
        },
        "icon" : "https://maps.gstatic.com/mapfiles/place_api/icons/v1/png_71/worship_general-71.png",
        "icon_background_color" : "#7B9EB0",
        "icon_mask_base_uri" : "https://maps.gstatic.com/mapfiles/place_api/icons/v2/worship_christian_pinlet",
        "name" : "Basilique Cathédrale de Saint-Denis",
        "opening_hours" : {
           "open_now" : true
        },
        "photos" : [
           {
              "height" : 4032,
              "html_attributions" : [
                 "\u003ca href=\"https://maps.google.com/maps/contrib/100315414812221028304\"\u003eFritz R\u003c/a\u003e"
              ],
              "photo_reference" : "Aap_uEAh7GuoizcLpkalWU5atizDm1cxWUKvhxrZbZ3qe-T0w2vI2EFl2Fi-CUvGL9f5MBtlrU9sHdRPXGZvI-dINHEVL8_r5bCKReIwaYmV57_daBeS3lfL01S5sIiiH1ZEcT_oYwa6sQcC6MKi46oX-qyBtC_IGFAhy0oL2ACFBg6CXzN2",
              "width" : 3024
           }
        ],
        "place_id" : "ChIJvRj8ErNu5kcRfOX_xli-__I",
        "plus_code" : {
           "compound_code" : "W9P5+5W Saint-Denis, France",
           "global_code" : "8FW4W9P5+5W"
        },
        "rating" : 4.6,
        "reference" : "ChIJvRj8ErNu5kcRfOX_xli-__I",
        "scope" : "GOOGLE",
        "types" : [
           "tourist_attraction",
           "church",
           "place_of_worship",
           "point_of_interest",
           "establishment"
        ],
        "user_ratings_total" : 4406,
        "vicinity" : "1 Rue de la Légion d'Honneur, Saint-Denis"
     },
     {
        "business_status" : "CLOSED_TEMPORARILY",
        "geometry" : {
           "location" : {
              "lat" : 48.9326429,
              "lng" : 2.3571928
           },
           "viewport" : {
              "northeast" : {
                 "lat" : 48.9340683302915,
                 "lng" : 2.358485730291502
              },
              "southwest" : {
                 "lat" : 48.9313703697085,
                 "lng" : 2.355787769708498
              }
           }
        },
        "icon" : "https://maps.gstatic.com/mapfiles/place_api/icons/v1/png_71/generic_business-71.png",
        "icon_background_color" : "#7B9EB0",
        "icon_mask_base_uri" : "https://maps.gstatic.com/mapfiles/place_api/icons/v2/generic_pinlet",
        "name" : "AFIJ Saint-Denis",
        "permanently_closed" : true,
        "photos" : [
           {
              "height" : 454,
              "html_attributions" : [
                 "\u003ca href=\"https://maps.google.com/maps/contrib/103796769143299812633\"\u003eAFIJ Saint-Denis\u003c/a\u003e"
              ],
              "photo_reference" : "Aap_uEBlx2gfjKysiuNiqr-lCgaYzuIa6JKvLrUT1FqLwU2CYzXDa-0UTVNn6uNZUGETGVlIAafuVUekak18Gv59Ws7JjjaykFsIephRVEEFk3BMfPFgOqc6hw1-uJ99MDVrbeo-KsEd4L07Axc2FVU0reUYv4nYusZb1UNWOu-cpfDm2wUN",
              "width" : 454
           }
        ],
        "place_id" : "ChIJEaFW7LNu5kcR_VcV5KMpksY",
        "plus_code" : {
           "compound_code" : "W9M4+3V Saint-Denis, France",
           "global_code" : "8FW4W9M4+3V"
        },
        "rating" : 4,
        "reference" : "ChIJEaFW7LNu5kcR_VcV5KMpksY",
        "scope" : "GOOGLE",
        "types" : [ "point_of_interest", "establishment" ],
        "user_ratings_total" : 1,
        "vicinity" : "36 Rue de la Légion d'Honneur, Saint-Denis"
     },
     {
        "business_status" : "CLOSED_TEMPORARILY",
        "geometry" : {
           "location" : {
              "lat" : 48.93650589999999,
              "lng" : 2.3562353
           },
           "viewport" : {
              "northeast" : {
                 "lat" : 48.9378423302915,
                 "lng" : 2.357503030291502
              },
              "southwest" : {
                 "lat" : 48.9351443697085,
                 "lng" : 2.354805069708498
              }
           }
        },
        "icon" : "https://maps.gstatic.com/mapfiles/place_api/icons/v1/png_71/shopping-71.png",
        "icon_background_color" : "#4B96F3",
        "icon_mask_base_uri" : "https://maps.gstatic.com/mapfiles/place_api/icons/v2/shopping_pinlet",
        "name" : "Sweety Shop",
        "permanently_closed" : true,
        "place_id" : "ChIJI7hFrbRu5kcRf0KCI-Rz7jY",
        "plus_code" : {
           "compound_code" : "W9P4+JF Saint-Denis, France",
           "global_code" : "8FW4W9P4+JF"
        },
        "rating" : 5,
        "reference" : "ChIJI7hFrbRu5kcRf0KCI-Rz7jY",
        "scope" : "GOOGLE",
        "types" : [
           "shoe_store",
           "point_of_interest",
           "clothing_store",
           "store",
           "establishment"
        ],
        "user_ratings_total" : 3,
        "vicinity" : "84 Rue Gabriel Péri, Saint-Denis"
     },
     {
        "business_status" : "OPERATIONAL",
        "geometry" : {
           "location" : {
              "lat" : 48.93672530000001,
              "lng" : 2.3581663
           },
           "viewport" : {
              "northeast" : {
                 "lat" : 48.93802423029151,
                 "lng" : 2.359515930291502
              },
              "southwest" : {
                 "lat" : 48.93532626970851,
                 "lng" : 2.356817969708498
              }
           }
        },
        "icon" : "https://maps.gstatic.com/mapfiles/place_api/icons/v1/png_71/bank-71.png",
        "icon_background_color" : "#909CE1",
        "icon_mask_base_uri" : "https://maps.gstatic.com/mapfiles/place_api/icons/v2/bank-intl_pinlet",
        "name" : "Caisse d'Epargne Saint-Denis",
        "opening_hours" : {
           "open_now" : false
        },
        "photos" : [
           {
              "height" : 4608,
              "html_attributions" : [
                 "\u003ca href=\"https://maps.google.com/maps/contrib/109969198979857615179\"\u003eHans Goeckner\u003c/a\u003e"
              ],
              "photo_reference" : "Aap_uEBPL4l7UA5iRVOMp20sMtRphCJ6JOwn-6Sjd_6Psq9KB3W_ioBAXCtTwfkaEBzL4uqs6-dBDpKklX0BuVTQoYWZ_H8K-Qi3s1M_-EczM8rfl7KolbP1iG8x1aRDbLK2zXrU_olY2U78v5HXU-F1xf-RHtY0TmjboDp3Ux9NkliYt8ER",
              "width" : 3072
           }
        ],
        "place_id" : "ChIJdRCcykxp5kcRJ2sYmwVaCZA",
        "plus_code" : {
           "compound_code" : "W9P5+M7 Saint-Denis, France",
           "global_code" : "8FW4W9P5+M7"
        },
        "rating" : 1.8,
        "reference" : "ChIJdRCcykxp5kcRJ2sYmwVaCZA",
        "scope" : "GOOGLE",
        "types" : [
           "bank",
           "atm",
           "finance",
           "insurance_agency",
           "health",
           "point_of_interest",
           "establishment"
        ],
        "user_ratings_total" : 47,
        "vicinity" : "19 Place Jean Jaurès, Saint-Denis"
     },
     {
        "business_status" : "OPERATIONAL",
        "geometry" : {
           "location" : {
              "lat" : 48.9364576,
              "lng" : 2.3542319
           },
           "viewport" : {
              "northeast" : {
                 "lat" : 48.9378436302915,
                 "lng" : 2.355741230291502
              },
              "southwest" : {
                 "lat" : 48.9351456697085,
                 "lng" : 2.353043269708498
              }
           }
        },
        "icon" : "https://maps.gstatic.com/mapfiles/place_api/icons/v1/png_71/shopping-71.png",
        "icon_background_color" : "#4B96F3",
        "icon_mask_base_uri" : "https://maps.gstatic.com/mapfiles/place_api/icons/v2/shopping_pinlet",
        "name" : "Jeff de Bruges",
        "opening_hours" : {
           "open_now" : false
        },
        "photos" : [
           {
              "height" : 810,
              "html_attributions" : [
                 "\u003ca href=\"https://maps.google.com/maps/contrib/114551162927975727188\"\u003eJeff de Bruges\u003c/a\u003e"
              ],
              "photo_reference" : "Aap_uEDqNlHNZkrfU-TFLoebBKOToQ-N73O8neLtKQiJRukrsPgIQOZ_26IKRM4BzRAKiizOrNswkEDDHNyToPlKp1Hu9NZtnH34_d94I4IWORIaffXh-CorRoVYqYn0m7kO5dqImLUFefDwcVH7RtwMf6Dc5rCgofdoW97kLXiwAmXJNlQ9",
              "width" : 1440
           }
        ],
        "place_id" : "ChIJw3591LRu5kcRNJfmbX1JhLs",
        "plus_code" : {
           "compound_code" : "W9P3+HM Saint-Denis, France",
           "global_code" : "8FW4W9P3+HM"
        },
        "rating" : 4.6,
        "reference" : "ChIJw3591LRu5kcRNJfmbX1JhLs",
        "scope" : "GOOGLE",
        "types" : [ "restaurant", "food", "point_of_interest", "store", "establishment" ],
        "user_ratings_total" : 27,
        "vicinity" : "57 Rue de la République, Saint-Denis"
     },
     {
        "business_status" : "OPERATIONAL",
        "geometry" : {
           "location" : {
              "lat" : 48.9338913,
              "lng" : 2.3565655
           },
           "viewport" : {
              "northeast" : {
                 "lat" : 48.9353183802915,
                 "lng" : 2.357763930291502
              },
              "southwest" : {
                 "lat" : 48.93262041970851,
                 "lng" : 2.355065969708498
              }
           }
        },
        "icon" : "https://maps.gstatic.com/mapfiles/place_api/icons/v1/png_71/worship_islam-71.png",
        "icon_background_color" : "#7B9EB0",
        "icon_mask_base_uri" : "https://maps.gstatic.com/mapfiles/place_api/icons/v2/worship_islam_pinlet",
        "name" : "Mosquée (Centre Socio-Culturel Tawhid)",
        "opening_hours" : {
           "open_now" : true
        },
        "photos" : [
           {
              "height" : 3024,
              "html_attributions" : [
                 "\u003ca href=\"https://maps.google.com/maps/contrib/100938135022499303375\"\u003eSami Samou\u003c/a\u003e"
              ],
              "photo_reference" : "Aap_uEDc-Pz6KaScKtofPNMFeHGqOhZ1j6SlVr64JZvRBX7lRNTFVHG8w6ezZUPflvu2XgNfX0nC5phlBACDnea9yKBS7geM93wp7uXv9Xya1R4jenu1Xwrqz4tn68PpAHThNfQnqkpEQgdYwULl-0HvRWoTZe_AK5wmgQxuMYfsC0G9Nh2X",
              "width" : 4032
           }
        ],
        "place_id" : "ChIJJZbXf7Ru5kcR_iHHZYo0zp4",
        "plus_code" : {
           "compound_code" : "W9M4+HJ Saint-Denis, France",
           "global_code" : "8FW4W9M4+HJ"
        },
        "rating" : 4.1,
        "reference" : "ChIJJZbXf7Ru5kcR_iHHZYo0zp4",
        "scope" : "GOOGLE",
        "types" : [
           "mosque",
           "book_store",
           "place_of_worship",
           "point_of_interest",
           "store",
           "establishment"
        ],
        "user_ratings_total" : 29,
        "vicinity" : "39 Rue de la Boulangerie, Saint-Denis"
     },
     {
        "business_status" : "OPERATIONAL",
        "geometry" : {
           "location" : {
              "lat" : 48.9361389,
              "lng" : 2.3569443
           },
           "viewport" : {
              "northeast" : {
                 "lat" : 48.93745403029151,
                 "lng" : 2.358256480291502
              },
              "southwest" : {
                 "lat" : 48.93475606970851,
                 "lng" : 2.355558519708498
              }
           }
        },
        "icon" : "https://maps.gstatic.com/mapfiles/place_api/icons/v1/png_71/shopping-71.png",
        "icon_background_color" : "#4B96F3",
        "icon_mask_base_uri" : "https://maps.gstatic.com/mapfiles/place_api/icons/v2/shopping_pinlet",
        "name" : "Phoneo St Denis",
        "opening_hours" : {
           "open_now" : false
        },
        "photos" : [
           {
              "height" : 3024,
              "html_attributions" : [
                 "\u003ca href=\"https://maps.google.com/maps/contrib/108472061930110153175\"\u003ePhoneo St Denis\u003c/a\u003e"
              ],
              "photo_reference" : "Aap_uEBiuFhRgfbPqb9Qi_scsrs34t2KDtfWwZAfEb5CfLr0yuIR73w2JKKZ33sCF8f3p_2A0BWhPosV1p4qzWAR8W9wfF4IxIYISQsf3T_IwMcTePqhYzAi4O1y9yA4dfjEb6ksIIt15B0wDd9EpesFawse5FfGM600s6DmJBS49-t4LrIH",
              "width" : 4032
           }
        ],
        "place_id" : "ChIJa-XIULNu5kcRBX6KQjLG4JI",
        "plus_code" : {
           "compound_code" : "W9P4+FQ Saint-Denis, France",
           "global_code" : "8FW4W9P4+FQ"
        },
        "rating" : 3.7,
        "reference" : "ChIJa-XIULNu5kcRBX6KQjLG4JI",
        "scope" : "GOOGLE",
        "types" : [ "point_of_interest", "store", "establishment" ],
        "user_ratings_total" : 38,
        "vicinity" : "8 Rue de la République, Saint-Denis"
     },
     {
        "business_status" : "OPERATIONAL",
        "geometry" : {
           "location" : {
              "lat" : 48.93705929999999,
              "lng" : 2.3560567
           },
           "viewport" : {
              "northeast" : {
                 "lat" : 48.93841458029149,
                 "lng" : 2.357436530291502
              },
              "southwest" : {
                 "lat" : 48.93571661970849,
                 "lng" : 2.354738569708498
              }
           }
        },
        "icon" : "https://maps.gstatic.com/mapfiles/place_api/icons/v1/png_71/bank-71.png",
        "icon_background_color" : "#909CE1",
        "icon_mask_base_uri" : "https://maps.gstatic.com/mapfiles/place_api/icons/v2/bank-intl_pinlet",
        "name" : "BNP Paribas - Saint Denis Basilique",
        "opening_hours" : {
           "open_now" : false
        },
        "photos" : [
           {
              "height" : 483,
              "html_attributions" : [
                 "\u003ca href=\"https://maps.google.com/maps/contrib/107094775969252171311\"\u003eBNP Paribas - Saint Denis Basilique\u003c/a\u003e"
              ],
              "photo_reference" : "Aap_uEBsbq7ApryIfiCH1LMmLFWumUOdViidPztBjx2z1ORL0sfviPh-vysgWEiDUmo9WZ3JjfNbBnygzv6LB_9mt3-49_rhcHKQxhx44wQ26fAvmHRWTT5AQgK5E0Pkkgz1p9o1Zaexby5xYous9TIa285My1mQ0_LF-47P7_AopqNWZzkZ",
              "width" : 859
           }
        ],
        "place_id" : "ChIJ77ovUEtp5kcRFcweDMFHxQc",
        "plus_code" : {
           "compound_code" : "W9P4+RC Saint-Denis, France",
           "global_code" : "8FW4W9P4+RC"
        },
        "rating" : 3.1,
        "reference" : "ChIJ77ovUEtp5kcRFcweDMFHxQc",
        "scope" : "GOOGLE",
        "types" : [
           "bank",
           "atm",
           "finance",
           "insurance_agency",
           "point_of_interest",
           "establishment"
        ],
        "user_ratings_total" : 39,
        "vicinity" : "111 Rue Gabriel Péri, Saint-Denis"
     },
     {
        "business_status" : "CLOSED_TEMPORARILY",
        "geometry" : {
           "location" : {
              "lat" : 48.93633010000001,
              "lng" : 2.352569
           },
           "viewport" : {
              "northeast" : {
                 "lat" : 48.9380817302915,
                 "lng" : 2.353974780291502
              },
              "southwest" : {
                 "lat" : 48.9353837697085,
                 "lng" : 2.351276819708498
              }
           }
        },
        "icon" : "https://maps.gstatic.com/mapfiles/place_api/icons/v1/png_71/shopping-71.png",
        "icon_background_color" : "#4B96F3",
        "icon_mask_base_uri" : "https://maps.gstatic.com/mapfiles/place_api/icons/v2/shopping_pinlet",
        "name" : "Indigo",
        "permanently_closed" : true,
        "place_id" : "ChIJ_YNXtUpp5kcRgOvEX-1yPQU",
        "plus_code" : {
           "compound_code" : "W9P3+G2 Saint-Denis, France",
           "global_code" : "8FW4W9P3+G2"
        },
        "reference" : "ChIJ_YNXtUpp5kcRgOvEX-1yPQU",
        "scope" : "GOOGLE",
        "types" : [ "point_of_interest", "clothing_store", "store", "establishment" ],
        "vicinity" : "73 Rue de la République, Saint-Denis"
     },
     {
        "business_status" : "OPERATIONAL",
        "geometry" : {
           "location" : {
              "lat" : 48.93488680000001,
              "lng" : 2.3577638
           },
           "viewport" : {
              "northeast" : {
                 "lat" : 48.93621983029149,
                 "lng" : 2.359255330291502
              },
              "southwest" : {
                 "lat" : 48.93352186970849,
                 "lng" : 2.356557369708498
              }
           }
        },
        "icon" : "https://maps.gstatic.com/mapfiles/place_api/icons/v1/png_71/civic_building-71.png",
        "icon_background_color" : "#7B9EB0",
        "icon_mask_base_uri" : "https://maps.gstatic.com/mapfiles/place_api/icons/v2/civic-bldg_pinlet",
        "name" : "Médiathèque du Centre Ville",
        "opening_hours" : {
           "open_now" : false
        },
        "photos" : [
           {
              "height" : 438,
              "html_attributions" : [
                 "\u003ca href=\"https://maps.google.com/maps/contrib/106532090052681456603\"\u003eMédiathèque du Centre Ville\u003c/a\u003e"
              ],
              "photo_reference" : "Aap_uEBve4-AHFB6kWdUrubCCiHJQavThoJTr99c-mW7LASRGIZYIJrbzLBiRxV5agae5jaEEftDGdGfvEjbpZkpMbnCxjue3JXR12omTXY-8dbtN_hC59paG8Qe10yk5CxwHsfrwdI6bC24PLZ--4IOp8RV_9LYX1TZk8tb4UsT5ndyqxkP",
              "width" : 658
           }
        ],
        "place_id" : "ChIJmSHGbLNu5kcRpzohbgTFYpE",
        "plus_code" : {
           "compound_code" : "W9M5+X4 Saint-Denis, France",
           "global_code" : "8FW4W9M5+X4"
        },
        "rating" : 4.4,
        "reference" : "ChIJmSHGbLNu5kcRpzohbgTFYpE",
        "scope" : "GOOGLE",
        "types" : [ "library", "point_of_interest", "establishment" ],
        "user_ratings_total" : 133,
        "vicinity" : "4 Place de la Légion d'Honneur, Saint-Denis"
     },
     {
        "business_status" : "OPERATIONAL",
        "geometry" : {
           "location" : {
              "lat" : 48.93626810000001,
              "lng" : 2.354914
           },
           "viewport" : {
              "northeast" : {
                 "lat" : 48.93766703029149,
                 "lng" : 2.356335380291502
              },
              "southwest" : {
                 "lat" : 48.9349690697085,
                 "lng" : 2.353637419708498
              }
           }
        },
        "icon" : "https://maps.gstatic.com/mapfiles/place_api/icons/v1/png_71/generic_business-71.png",
        "icon_background_color" : "#7B9EB0",
        "icon_mask_base_uri" : "https://maps.gstatic.com/mapfiles/place_api/icons/v2/generic_pinlet",
        "name" : "Yves Rocher",
        "opening_hours" : {
           "open_now" : true
        },
        "photos" : [
           {
              "height" : 1005,
              "html_attributions" : [
                 "\u003ca href=\"https://maps.google.com/maps/contrib/115588901854183264797\"\u003eYves Rocher\u003c/a\u003e"
              ],
              "photo_reference" : "Aap_uECZGtPhAcg6FvV_0ptmx1JXZIEZebdV-SIYS8CJXlQqJuVzmOd-napRY0wQ9KA0UrYzeGrqY2FavIlF01tLKK71mZx7aLeepydbaILI9m3RP25zMY0ubkKe-w9O6GhI8w9U7Sr1h0Hkfmx5QobwvWb4r4MAxvygWvvzLg0FQ9SEoC_j",
              "width" : 1788
           }
        ],
        "place_id" : "ChIJ04dPLbVu5kcRgqp5434IsZY",
        "plus_code" : {
           "compound_code" : "W9P3+GX Saint-Denis, France",
           "global_code" : "8FW4W9P3+GX"
        },
        "rating" : 3.8,
        "reference" : "ChIJ04dPLbVu5kcRgqp5434IsZY",
        "scope" : "GOOGLE",
        "types" : [ "beauty_salon", "point_of_interest", "establishment" ],
        "user_ratings_total" : 214,
        "vicinity" : "45 Rue de la République, Saint-Denis"
     },
     {
        "business_status" : "OPERATIONAL",
        "geometry" : {
           "location" : {
              "lat" : 48.93455220000001,
              "lng" : 2.3559837
           },
           "viewport" : {
              "northeast" : {
                 "lat" : 48.93590348029149,
                 "lng" : 2.357283680291502
              },
              "southwest" : {
                 "lat" : 48.9332055197085,
                 "lng" : 2.354585719708498
              }
           }
        },
        "icon" : "https://maps.gstatic.com/mapfiles/place_api/icons/v1/png_71/generic_business-71.png",
        "icon_background_color" : "#7B9EB0",
        "icon_mask_base_uri" : "https://maps.gstatic.com/mapfiles/place_api/icons/v2/generic_pinlet",
        "name" : "Cabinet Poncelet et Cie",
        "opening_hours" : {
           "open_now" : false
        },
        "place_id" : "ChIJS_fuhbRu5kcRi8fEAS09Rt4",
        "plus_code" : {
           "compound_code" : "W9M4+R9 Saint-Denis, France",
           "global_code" : "8FW4W9M4+R9"
        },
        "rating" : 1.4,
        "reference" : "ChIJS_fuhbRu5kcRi8fEAS09Rt4",
        "scope" : "GOOGLE",
        "types" : [ "point_of_interest", "establishment" ],
        "user_ratings_total" : 29,
        "vicinity" : "60 Rue Gabriel Péri, Saint-Denis"
     },
     {
        "business_status" : "OPERATIONAL",
        "geometry" : {
           "location" : {
              "lat" : 48.9361,
              "lng" : 2.3502127
           },
           "viewport" : {
              "northeast" : {
                 "lat" : 48.93749373029149,
                 "lng" : 2.351435480291502
              },
              "southwest" : {
                 "lat" : 48.93479576970849,
                 "lng" : 2.348737519708498
              }
           }
        },
        "icon" : "https://maps.gstatic.com/mapfiles/place_api/icons/v1/png_71/bank-71.png",
        "icon_background_color" : "#909CE1",
        "icon_mask_base_uri" : "https://maps.gstatic.com/mapfiles/place_api/icons/v2/bank-intl_pinlet",
        "name" : "Banque Populaire Rives de Paris",
        "opening_hours" : {
           "open_now" : false
        },
        "place_id" : "ChIJHR_xsMpu5kcRMIYsR01d8oc",
        "plus_code" : {
           "compound_code" : "W9P2+C3 Saint-Denis, France",
           "global_code" : "8FW4W9P2+C3"
        },
        "rating" : 2.7,
        "reference" : "ChIJHR_xsMpu5kcRMIYsR01d8oc",
        "scope" : "GOOGLE",
        "types" : [ "bank", "atm", "finance", "point_of_interest", "establishment" ],
        "user_ratings_total" : 12,
        "vicinity" : "32 Boulevard Jules Guesde, Saint-Denis"
     },
     {
        "business_status" : "OPERATIONAL",
        "geometry" : {
           "location" : {
              "lat" : 48.9341935,
              "lng" : 2.3559071
           },
           "viewport" : {
              "northeast" : {
                 "lat" : 48.93555748029149,
                 "lng" : 2.357247330291502
              },
              "southwest" : {
                 "lat" : 48.9328595197085,
                 "lng" : 2.354549369708498
              }
           }
        },
        "icon" : "https://maps.gstatic.com/mapfiles/place_api/icons/v1/png_71/generic_business-71.png",
        "icon_background_color" : "#7B9EB0",
        "icon_mask_base_uri" : "https://maps.gstatic.com/mapfiles/place_api/icons/v2/generic_pinlet",
        "name" : "SAINT-DENIS IMMOBILIER",
        "opening_hours" : {
           "open_now" : true
        },
        "photos" : [
           {
              "height" : 768,
              "html_attributions" : [
                 "\u003ca href=\"https://maps.google.com/maps/contrib/105429675633777140784\"\u003eSAINT-DENIS IMMOBILIER\u003c/a\u003e"
              ],
              "photo_reference" : "Aap_uEDzDRcWRcLtaAOoLeGt40l9JsB-gFTY0osOTMJlCNbFqIyIyHiLbnlymsfhH_4ATpRhFTRVM3boLcjjYPjeUjENIxgrIjatRR7P4y4cUssaM6L1hYxYZz7nDVAWeeiYcLwuT6ed8HxK3Omz1URYG1BDAtn8UxBjM6951GK7H7_soN8f",
              "width" : 1024
           }
        ],
        "place_id" : "ChIJYTE2hrRu5kcRsKciQf10Jek",
        "plus_code" : {
           "compound_code" : "W9M4+M9 Saint-Denis, France",
           "global_code" : "8FW4W9M4+M9"
        },
        "rating" : 4.7,
        "reference" : "ChIJYTE2hrRu5kcRsKciQf10Jek",
        "scope" : "GOOGLE",
        "types" : [ "real_estate_agency", "point_of_interest", "establishment" ],
        "user_ratings_total" : 161,
        "vicinity" : "58 Rue Gabriel Péri, Saint-Denis"
     },
     {
        "business_status" : "OPERATIONAL",
        "geometry" : {
           "location" : {
              "lat" : 48.9361596,
              "lng" : 2.356946200000001
           },
           "viewport" : {
              "northeast" : {
                 "lat" : 48.9374643802915,
                 "lng" : 2.358257430291502
              },
              "southwest" : {
                 "lat" : 48.9347664197085,
                 "lng" : 2.355559469708498
              }
           }
        },
        "icon" : "https://maps.gstatic.com/mapfiles/place_api/icons/v1/png_71/generic_business-71.png",
        "icon_background_color" : "#7B9EB0",
        "icon_mask_base_uri" : "https://maps.gstatic.com/mapfiles/place_api/icons/v2/generic_pinlet",
        "name" : "Lazer",
        "opening_hours" : {
           "open_now" : false
        },
        "place_id" : "ChIJHxsNRNj-BEgRD5JzM7ag2BQ",
        "plus_code" : {
           "compound_code" : "W9P4+FQ Saint-Denis, France",
           "global_code" : "8FW4W9P4+FQ"
        },
        "rating" : 2.7,
        "reference" : "ChIJHxsNRNj-BEgRD5JzM7ag2BQ",
        "scope" : "GOOGLE",
        "types" : [ "point_of_interest", "establishment" ],
        "user_ratings_total" : 23,
        "vicinity" : "8 Rue de la République, Saint-Denis"
     },
     {
        "business_status" : "OPERATIONAL",
        "geometry" : {
           "location" : {
              "lat" : 48.9319365,
              "lng" : 2.3560103
           },
           "viewport" : {
              "northeast" : {
                 "lat" : 48.9332500802915,
                 "lng" : 2.357344530291501
              },
              "southwest" : {
                 "lat" : 48.9305521197085,
                 "lng" : 2.354646569708498
              }
           }
        },
        "icon" : "https://maps.gstatic.com/mapfiles/place_api/icons/v1/png_71/generic_business-71.png",
        "icon_background_color" : "#7B9EB0",
        "icon_mask_base_uri" : "https://maps.gstatic.com/mapfiles/place_api/icons/v2/generic_pinlet",
        "name" : "La Miel",
        "opening_hours" : {
           "open_now" : true
        },
        "photos" : [
           {
              "height" : 609,
              "html_attributions" : [
                 "\u003ca href=\"https://maps.google.com/maps/contrib/116315401300854079835\"\u003eLa Miel\u003c/a\u003e"
              ],
              "photo_reference" : "Aap_uECbaamMe55Wq5MIhSUeTeDx5p2HZqLwq_iH-WxxD6RGl1MmWqLbkTWwyaUNNHOtCZNQ-z52Q6weJqvbvRoocndLDVLB3lMXGDT9H0dg0Gn9WoFn9ca_Dp_oL7Fkm_hEddi82U1dKZoiRkvbjtIH6yuOFqiDNnPCq-czY8iI7zVRb-eG",
              "width" : 960
           }
        ],
        "place_id" : "ChIJb88hXKZu5kcRHsZ96hmHMQU",
        "plus_code" : {
           "compound_code" : "W9J4+QC Saint-Denis, France",
           "global_code" : "8FW4W9J4+QC"
        },
        "rating" : 4.5,
        "reference" : "ChIJb88hXKZu5kcRHsZ96hmHMQU",
        "scope" : "GOOGLE",
        "types" : [ "point_of_interest", "establishment" ],
        "user_ratings_total" : 27,
        "vicinity" : "20 Rue Gabriel Péri, Saint-Denis"
     },
     {
        "business_status" : "OPERATIONAL",
        "geometry" : {
           "location" : {
              "lat" : 48.9354919,
              "lng" : 2.3503434
           },
           "viewport" : {
              "northeast" : {
                 "lat" : 48.9368264302915,
                 "lng" : 2.351551980291502
              },
              "southwest" : {
                 "lat" : 48.9341284697085,
                 "lng" : 2.348854019708498
              }
           }
        },
        "icon" : "https://maps.gstatic.com/mapfiles/place_api/icons/v1/png_71/generic_business-71.png",
        "icon_background_color" : "#7B9EB0",
        "icon_mask_base_uri" : "https://maps.gstatic.com/mapfiles/place_api/icons/v2/generic_pinlet",
        "name" : "Agora SEA | Expertise Comptable | Cabinet de Saint-Denis 93200",
        "opening_hours" : {
           "open_now" : false
        },
        "photos" : [
           {
              "height" : 3464,
              "html_attributions" : [
                 "\u003ca href=\"https://maps.google.com/maps/contrib/100397221174911544857\"\u003eAgora SEA | Expertise Comptable | Cabinet de Saint-Denis 93200\u003c/a\u003e"
              ],
              "photo_reference" : "Aap_uEDup-kz1sO_TYS1xhdOUxg7t2OrTDpkBNovDIsnVirxYaWofK9kCXMuLxb6__TMk98l59zWiT4Shl5YyAP45kvhj6apaAfVfDUaGxM9xAYFMAr64Te0jsBC5MorEg5B7R6bBd_JKX1u5domHByU3-HgQBkg4wAX38Y9OQN5WToDeZjR",
              "width" : 4618
           }
        ],
        "place_id" : "ChIJkTVClspu5kcROvPjpGkiZCs",
        "plus_code" : {
           "compound_code" : "W9P2+54 Saint-Denis, France",
           "global_code" : "8FW4W9P2+54"
        },
        "rating" : 3.6,
        "reference" : "ChIJkTVClspu5kcROvPjpGkiZCs",
        "scope" : "GOOGLE",
        "types" : [ "accounting", "finance", "point_of_interest", "establishment" ],
        "user_ratings_total" : 9,
        "vicinity" : "26 Boulevard Jules Guesde, Saint-Denis"
     }
  ],
  "status" : "OK"
}

console.log(data.results[3].name);
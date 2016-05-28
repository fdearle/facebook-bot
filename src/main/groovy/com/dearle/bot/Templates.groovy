package com.dearle.bot

class Templates {
    def static HarryPotter = [
            template_type: "generic",
            elements: [
                    [
                            title: "You're a wizard, Harry.",
                            image_url:"http://nerdist.com/wp-content/uploads/2015/06/Wizarding-World-of-Harry-Potter.jpg",
                            buttons: [
                                    [
                                            type: "web_url",
                                            url: "https://twitter.com/official_hagrid",
                                            title: "See Hagrid's Twitter"
                                    ]
                            ]
                    ]
            ]
    ]


    def static Doritos = [
            template_type: "generic",
            elements: [
                    [
                            title: "Doritos!",
                            image_url:"http://i5.walmartimages.com/dfw/dce07b8c-c4fc/k2-_37652a08-291a-440a-9034-7e7993a11b4b.v1.jpg",
                            buttons: [
                                    [
                                            type: "web_url",
                                            url: "https://www.doritos.co.uk/",
                                            title: "YUM!"
                                    ]
                            ]
                    ]
            ]
    ]
}

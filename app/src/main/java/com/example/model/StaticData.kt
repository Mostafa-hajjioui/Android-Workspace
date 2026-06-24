package com.example.model

object StaticData {

    // 5 High-Impact Moroccan Situational Diagnostic Questions
    val diagnosticQuestions = listOf(
        QuizQuestion(
            id = 1,
            questionText = "A foreign tourist at Jemaa el-Fnaa in Marrakech is lost and asks you for directions to the Majorelle Garden. Which response is most professional, polite, and grammatically complete?",
            situationalContext = "📍 Location: Jemaa el-Fnaa, Marrakech",
            options = listOf(
                "Go direct, then left, very simple. Map is good.",
                "If you take a taxi, it is easier. But you can walk there if you want.",
                "To get to the Majorelle Garden, walk straight toward the mosque, take the first exit, or take a quick taxi ride. It's about a 20-minute walk.",
                "I don't know, ask the shopkeeper over there."
            ),
            correctOptionIndex = 2,
            explanation = "Option C uses full sentence structures with polite transition phrases, demonstrating clear B1+ level communicative competence."
        ),
        QuizQuestion(
            id = 2,
            questionText = "You are working on a Discord server with a peer developer from London on a solar-charger app for Atlas villages. You notice a bug in their dashboard layout. How do you professionally point it out?",
            situationalContext = "💬 Context: Discord Dev Channel",
            options = listOf(
                "Your code is completely broken. Please delete it and redo.",
                "I noticed a small rendering issue on the dashboard layout. Could we check the column constraints together when you're free?",
                "Layout is bad. Screen doesn't look like what we wanted.",
                "Never mind, I will write it myself. Don't worry about it."
            ),
            correctOptionIndex = 1,
            explanation = "Option B represents high-fidelity professional collaboration. It uses constructive phrasing ('noticed a small rendering issue') and collaborative suggestions ('Could we check... together')."
        ),
        QuizQuestion(
            id = 3,
            questionText = "You are writing a LinkedIn post for a peer-to-peer tutoring platform you built for students in Casablanca. Which headline is most persuasive and attention-grabbing?",
            situationalContext = "🚀 Context: LinkedIn Startup Launch",
            options = listOf(
                "We made an app for students in Casablanca. Download it.",
                "Tired of study stress? Unlock your potential with CASABLANCA TUTORS—a modern, student-led study community designed by youth, for youth. Join our summer cohort today!",
                "Tutoring application Casablanca. It is free. Please join.",
                "School is hard. Our application has answers for exams."
            ),
            correctOptionIndex = 1,
            explanation = "Option B employs persuasive vocabulary ('unlock your potential', 'student-led', 'cohort') and directly targets the audience with emotional resonance."
        ),
        QuizQuestion(
            id = 4,
            questionText = "An interviewer for an international English-taught tech scholarship in Rabat asks you: 'Where do you see yourself in five years?' Which answer is structured best?",
            situationalContext = "🎓 Context: Scholarship Interview, Rabat",
            options = listOf(
                "I want to have a tech job, earning a lot of money in Rabat or Tangier.",
                "I will probably be finished with university and maybe working as a developer somewhere.",
                "In five years, I aim to have graduated with a degree in Computer Science and be contributing to Morocco's growing green-tech sector by designing localized software solutions.",
                "Five years is too far away to plan, but I like coding websites now."
            ),
            correctOptionIndex = 2,
            explanation = "Option C demonstrates C1 professional clarity: a structured timeline, a specific academic milestone, and a clear vision aligned with national tech growth."
        ),
        QuizQuestion(
            id = 5,
            questionText = "Your foreign peer is amazed by the hospitality of Moroccan families and comments: 'Everyone treats me like royalty!' Which idiomatic expression best fits a polite and eloquent reply?",
            situationalContext = "☕ Context: Café Chat in Tangier",
            options = listOf(
                "Yes, Moroccan people have no choice, we must do this.",
                "Moroccan hospitality is second to nature. Welcoming guests with open arms is a core value we take pride in.",
                "Our food is very good and tajine is famous, so we feed everyone.",
                "It is just normal. Don't worry about it."
            ),
            correctOptionIndex = 1,
            explanation = "Option B correctly employs the idiom 'second to nature' (or 'second to none') and expresses complex cultural concepts elegantly."
        )
    )

    // Personalized Curriculum Map categorized by CEFR Levels
    fun getCurriculumForLevel(level: ProficiencyLevel): List<CourseWeek> {
        return when (level) {
            ProficiencyLevel.A1_A2 -> listOf(
                CourseWeek(
                    weekNumber = 1,
                    weekTitle = "Marrakech Welcome: Foundations of Self-Expression",
                    focusArea = "Confidence Building & Conversational Grammar",
                    tasks = listOf(
                        CourseTask("a1_w1_1", "The Pitch: Introducing Yourself", "Master the perfect 30-second introduction emphasizing your name, hometown, and hobbies with correct tense structure.", "15 mins", TaskType.CORE_LESSON),
                        CourseTask("a1_w1_2", "Moroccan Explorer Sandbox", "Learn 15 dynamic adjectives to describe Moroccan cities, culture, and foods to tourists.", "20 mins", TaskType.CYBER_CHALLENGE),
                        CourseTask("a1_w1_3", "Café Marrakech Scenario", "Roleplay task: Order food, ask for directions, and converse with a foreign diner at an outdoor cafe.", "30 mins", TaskType.MOROCCAN_PITCH)
                    )
                ),
                CourseWeek(
                    weekNumber = 2,
                    weekTitle = "Tech Sandbox: Coding Vocabulary & Digital Tools",
                    focusArea = "Tech Foundation & Digital Fluency",
                    tasks = listOf(
                        CourseTask("a1_w2_1", "Byte-Sized Vocab", "Learn the primary terminology used in app development, UI design, and online collaboration (e.g., buttons, grids, databases).", "15 mins", TaskType.CORE_LESSON),
                        CourseTask("a1_w2_2", "Discord Onboarding Drill", "Write a mock introduction message for an international coding discord server to find peers.", "20 mins", TaskType.CYBER_CHALLENGE),
                        CourseTask("a1_w2_3", "Explain Your App Idea", "Draft a 5-sentence explanation of an app idea you want to build this summer using tech nouns.", "25 mins", TaskType.MOROCCAN_PITCH)
                    )
                ),
                CourseWeek(
                    weekNumber = 3,
                    weekTitle = "Youth Forums: Sharing Ideas & Collaboration",
                    focusArea = "Expressing Opinions & Team Dynamics",
                    tasks = listOf(
                        CourseTask("a1_w3_1", "Agree/Disagree Formulas", "Master 6 polite phrases to agree or disagree with friends without causing offense.", "15 mins", TaskType.CORE_LESSON),
                        CourseTask("a1_w3_2", "Eco-Morocco Discussion", "Read a simple text about plastic bags in Morocco, and select three arguments in favor of paper bags.", "20 mins", TaskType.CYBER_CHALLENGE),
                        CourseTask("a1_w3_3", "Youth Club Debate", "Write a paragraph explaining why your school needs an after-school coding club in Tangier.", "30 mins", TaskType.MOROCCAN_PITCH)
                    )
                ),
                CourseWeek(
                    weekNumber = 4,
                    weekTitle = "Local Action: The Final Grand Showcase",
                    focusArea = "Public Speaking & Graduation Prep",
                    tasks = listOf(
                        CourseTask("a1_w4_1", "Presentation Roadmap", "Learn how to structure a presentation: Hook, Core Message, and Call-to-Action.", "15 mins", TaskType.CORE_LESSON),
                        CourseTask("a1_w4_2", "Visual Slide Draft", "Write 3 bullet points describing what your slides would say for an eco-tourism project.", "20 mins", TaskType.CYBER_CHALLENGE),
                        CourseTask("a1_w4_3", "The Graduation Video", "Record or write your final 1-minute speech pitching your summer achievements.", "35 mins", TaskType.MOROCCAN_PITCH)
                    )
                )
            )

            ProficiencyLevel.B1_B2 -> listOf(
                CourseWeek(
                    weekNumber = 1,
                    weekTitle = "Cultural Bridges: Articulating Moroccan Heritage",
                    focusArea = "Narrative Structure & Vivid Storytelling",
                    tasks = listOf(
                        CourseTask("b1_w1_1", "The Art of Storytelling", "Examine narrative arcs and learn how to use sensory adjectives to describe Moroccan architecture, cuisine, and history.", "20 mins", TaskType.CORE_LESSON),
                        CourseTask("b1_w1_2", "The Tour Guide Guide", "Write a 150-word description of Rabat's Oudaya Kasbah, highlighting details for a first-time foreign visitor.", "25 mins", TaskType.CYBER_CHALLENGE),
                        CourseTask("b1_w1_3", "Cultural Ambassador Interview", "Respond to the question: 'What makes Moroccan youth unique in the Mediterranean region?'", "30 mins", TaskType.MOROCCAN_PITCH)
                    )
                ),
                CourseWeek(
                    weekNumber = 2,
                    weekTitle = "The Startup Blueprint: Explaining Software",
                    focusArea = "Technical Explanations & Bug Management",
                    tasks = listOf(
                        CourseTask("b1_w2_1", "Architecture Speak", "Master the nouns and verbs used to describe cloud databases, APIs, responsive designs, and system architectures.", "20 mins", TaskType.CORE_LESSON),
                        CourseTask("b1_w2_2", "The Code Review Review", "Review a mock buggy code file and write 3 polite, clear bug reports explaining the issue to a teammate.", "25 mins", TaskType.CYBER_CHALLENGE),
                        CourseTask("b1_w2_3", "Tech Launch Pitch", "Pitch an e-commerce platform that sells traditional Moroccan handicrafts (Zellij) worldwide.", "35 mins", TaskType.MOROCCAN_PITCH)
                    )
                ),
                CourseWeek(
                    weekNumber = 3,
                    weekTitle = "The Debating Hub: Arguing Climate Change",
                    focusArea = "Persuasion, Statistics & Counter-Arguments",
                    tasks = listOf(
                        CourseTask("b1_w3_1", "Data & Statistics", "Learn how to weave statistics, percentages, and global targets (like Paris Agreement) into speech.", "20 mins", TaskType.CORE_LESSON),
                        CourseTask("b1_w3_2", "Solar Energy Solar Panel Lab", "Draft a short comparative analysis of solar vs wind energy efficiency in the Ouarzazate Noor plant.", "25 mins", TaskType.CYBER_CHALLENGE),
                        CourseTask("b1_w3_3", "Town Hall Debate Simulation", "Represent Moroccan youth advocating for plastic-free oceans in a written petition to the local commune.", "30 mins", TaskType.MOROCCAN_PITCH)
                    )
                ),
                CourseWeek(
                    weekNumber = 4,
                    weekTitle = "Atlas Pitch Deck: Presenting to Investors",
                    focusArea = "Presentation Delivery & Impromptu Q&A",
                    tasks = listOf(
                        CourseTask("b1_w4_1", "The Elevator Pitch Formula", "Study the exact structure of successful startup pitches: Problem, Solution, Market, Traction, Ask.", "20 mins", TaskType.CORE_LESSON),
                        CourseTask("b1_w4_2", "Handle Tough Questions", "Draft answers to 3 tricky investor questions (e.g., 'How will you compete with Amazon?').", "25 mins", TaskType.CYBER_CHALLENGE),
                        CourseTask("b1_w4_3", "The 3-Minute Capstone Pitch", "Deliver a structured slide-deck script pitching a Moroccan health-tech startup.", "40 mins", TaskType.MOROCCAN_PITCH)
                    )
                )
            )

            ProficiencyLevel.C1_C2 -> listOf(
                CourseWeek(
                    weekNumber = 1,
                    weekTitle = "Global Rhetoric & Persuasive Oratory",
                    focusArea = "Advanced Speech Structure & Soundbites",
                    tasks = listOf(
                        CourseTask("c1_w1_1", "Aristotle's Rhetoric in Action", "Examine how ethos, pathos, and logos are used by modern tech CEOs (e.g., Steve Jobs, Satya Nadella) during keynotes.", "25 mins", TaskType.CORE_LESSON),
                        CourseTask("c1_w1_2", "Rhetorical Devices Lab", "Re-write a standard factual text using anaphora, hyperbole, and metaphor to make it captivating.", "30 mins", TaskType.CYBER_CHALLENGE),
                        CourseTask("c1_w1_3", "The Leadership Address", "Write a 3-minute visionary speech calling for tech education equality across North Africa.", "45 mins", TaskType.MOROCCAN_PITCH)
                    )
                ),
                CourseWeek(
                    weekNumber = 2,
                    weekTitle = "Technical Mastery: Technical Documentation & API",
                    focusArea = "Complex Professional Writing & Spec Drafting",
                    tasks = listOf(
                        CourseTask("c1_w2_1", "Technical Specifications drafting", "Analyze the grammar of technical specs: passive voice, absolute clarity, concise lists, and unambiguous variables.", "25 mins", TaskType.CORE_LESSON),
                        CourseTask("c1_w2_2", "Draft API Documentation", "Write a beautiful markdown API endpoint specification for a Moroccan bus-tracker system.", "30 mins", TaskType.CYBER_CHALLENGE),
                        CourseTask("c1_w2_3", "The Peer Code Review", "Draft an intensive, polite, but highly technical feedback report on a complex git pull request.", "40 mins", TaskType.MOROCCAN_PITCH)
                    )
                ),
                CourseWeek(
                    weekNumber = 3,
                    weekTitle = "Academic Synthesizing & University Forums",
                    focusArea = "Critical Research Analysis & IELTS/SAT Prep",
                    tasks = listOf(
                        CourseTask("c1_w3_1", "Synthesis of Source Texts", "Learn to read three differing academic research papers and synthesize their arguments into a cohesive literature review.", "25 mins", TaskType.CORE_LESSON),
                        CourseTask("c1_w3_2", "IELTS Essay Breakdown", "Write a 250-word formal academic essay debating whether artificial intelligence will replace teachers.", "35 mins", TaskType.CYBER_CHALLENGE),
                        CourseTask("c1_w3_3", "The Oxford Debate Trial", "Refute a formal opposition claim asserting that developing nations should slow down tech adoption to save manual jobs.", "40 mins", TaskType.MOROCCAN_PITCH)
                    )
                ),
                CourseWeek(
                    weekNumber = 4,
                    weekTitle = "The Capstone Spark: Global Venture Pitching",
                    focusArea = "Elite Leadership Showcase & Certificate Graduation",
                    tasks = listOf(
                        CourseTask("c1_w4_1", "Ventures & Venture Capitalists", "Learn the complex financial vocabulary of tech funding: seed, Series A, valuations, burn rates, equity dilution.", "25 mins", TaskType.CORE_LESSON),
                        CourseTask("c1_w4_2", "The Pitch Defense Room", "Write replies to highly skeptical venture capitalist inquiries questioning product market fit in Africa.", "30 mins", TaskType.CYBER_CHALLENGE),
                        CourseTask("c1_w4_3", "The Ultimate Capstone Presentation", "Draft the complete spoken script and visual slides outline for your graduation venture.", "50 mins", TaskType.MOROCCAN_PITCH)
                    )
                )
            )
        }
    }
}

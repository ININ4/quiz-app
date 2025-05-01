$(document).ready(function () {
  let playerName = "";
  let questions = [];
  let currentIndex = 0;
  let score = 0;

  function showOnly(panelId) {
    $("#main-menu, #name-panel, #single-panel, #multi-panel, #how-panel, #result-panel").hide();
    $(panelId).show();
  }

  $("#single-btn").click(() => showOnly("#name-panel"));
  $("#multi-btn").click(() => showOnly("#multi-panel"));
  $("#how-btn").click(() => showOnly("#how-panel"));

  $("#start-btn").click(function () {
    const name = $("#player-name").val().trim();
    if (!name) {
      alert("Wpisz swoje imię.");
      return;
    }
    playerName = name;
    showOnly("#single-panel");
    loadQuestions();
  });

  async function loadQuestions() {
    try {
      const res = await fetch("/api/quiz/all");
      questions = await res.json();
      currentIndex = 0;
      score = 0;
      showQuestion();
    } catch (error) {
      $("#question-box").html("<p>Błąd podczas pobierania pytań.</p>");
      console.error("Błąd:", error);
    }
  }

  function showQuestion() {
    if (currentIndex >= questions.length) {
      submitResult();
      return;
    }

    const question = questions[currentIndex];
    let html = `<p>${question.question}</p>`;

    for (const [key, value] of Object.entries(question.options)) {
      html += `
        <div class="form-check text-start">
          <input class="form-check-input" type="radio" name="answer" value="${key}" id="opt-${key}">
          <label class="form-check-label" for="opt-${key}">
            ${key}: ${value}
          </label>
        </div>
      `;
    }

    html += `<button class="btn btn-success mt-3" id="submit-btn">Zatwierdź</button>`;
    $("#question-box").html(html);

    $("#submit-btn").click(async function () {
      const selected = $("input[name='answer']:checked").val();
      if (!selected) {
        alert("Wybierz odpowiedź.");
        return;
      }

      const request = {
        questionId: question.id,
        selectedAnswer: selected
      };

      try {
        const res = await fetch("/api/quiz/answer", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(request)
        });

        const result = await res.json();

        if (result.correct) {
          score++;
          alert("Poprawna odpowiedź!");
        } else {
          alert("Niepoprawna. Poprawna odpowiedź to: " + result.correctAnswer);
        }

        currentIndex++;
        showQuestion();
      } catch (err) {
        console.error("Błąd podczas sprawdzania odpowiedzi:", err);
      }
    });
  }

  async function submitResult() {
    showOnly("#result-panel");

    const payload = {
      playerName: playerName,
      score: score,
      total: questions.length
    };

    try {
      await fetch("/api/quiz/result", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload)
      });
    } catch (e) {
      console.error("Nie udało się zapisać wyniku:", e);
    }

    $("#score-box").html(`<p>${playerName}, Twój wynik to ${score}/${questions.length}</p>`);
    loadRanking();
  }

  async function loadRanking() {
    try {
      const res = await fetch("/api/quiz/results");
      const results = await res.json();
      results.sort((a, b) => b.score - a.score);

      let html = "";
      for (const r of results) {
        const date = r.timestamp || "brak daty";
        html += `<tr><td>${r.playerName}</td><td>${r.score}/${r.total}</td><td>${date}</td></tr>`;
      }

      $("#ranking-table tbody").html(html);
    } catch (err) {
      console.error("Błąd pobierania rankingu:", err);
      $("#ranking-table tbody").html("<tr><td colspan='3'>Błąd ładowania danych</td></tr>");
    }
  }

  // przyciski powrotu
  $("#back-from-how").click(() => showOnly("#main-menu"));
  $("#back-from-multi").click(() => showOnly("#main-menu"));
  $("#back-from-result").click(() => showOnly("#main-menu"));


});

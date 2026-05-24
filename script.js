// ── REPO FILES ──────────────────────────────────────────────
const repoFiles = [
  { path: "README.md",             desc: "Portfolio overview, skills demonstrated, and recruiter-facing summary", status: "wip" },
  { path: "project/README.md",     desc: "Project overview, problem statement, and team information", status: "planned" },
  { path: "project/docs/",         desc: "UML diagrams, design documents, and architecture specifications", status: "planned" },
  { path: "project/src/",          desc: "Java source code and implementation files", status: "planned" },
  { path: "homeworks/hw1/",        desc: "Homework 1 — OOP principles and object-oriented concepts", status: "planned" },
  { path: "homeworks/hw2/",        desc: "Homework 2 — UML diagram specifications and modeling", status: "planned" },
  { path: "homeworks/hw3/",        desc: "Homework 3 — Design pattern implementation in Java", status: "planned" },
  { path: "homeworks/hw4/",        desc: "Homework 4 — Peer-reviewed assignment", status: "planned" },
  { path: "notes/ooad/",           desc: "OOAD textbook chapter notes (Booch et al.)", status: "wip" },
  { path: "notes/design-patterns/",desc: "Design patterns textbook notes (Wengner)", status: "planned" },
  { path: "notes/uml/",            desc: "UML reference diagrams and modeling notes", status: "planned" },
  { path: "notes/architecture/",   desc: "Software architectural styles comparison and documentation", status: "planned" },
];

const statusLabel = { wip: "In Progress", done: "Complete", planned: "Planned" };

function renderList(items) {
  const list = document.getElementById("fileList");
  const noResults = document.getElementById("noResults");
  if (items.length === 0) {
    list.innerHTML = "";
    noResults.style.display = "block";
    return;
  }
  noResults.style.display = "none";
  list.innerHTML = items.map(f => `
    <li class="file-item">
      <div class="file-info">
        <span class="file-path">${f.path}</span>
        <span class="file-desc">${f.desc}</span>
      </div>
      <span class="file-status status-${f.status}">${statusLabel[f.status]}</span>
    </li>
  `).join("");
}

renderList(repoFiles);

document.getElementById("searchInput").addEventListener("input", function () {
  const q = this.value.toLowerCase().trim();
  if (!q) { renderList(repoFiles); return; }
  renderList(repoFiles.filter(f =>
    f.path.toLowerCase().includes(q) || f.desc.toLowerCase().includes(q)
  ));
});

// ── BOOKS & NOTES DATA ──────────────────────────────────────
const books = [
  {
    id: "ooad",
    title: "Object-Oriented Analysis and Design with Applications",
    edition: "3rd Edition",
    authors: "Grady Booch, Robert A. Maksimchuk, Michael W. Engle",
    year: 2007,
    publisher: "Addison-Wesley",
    role: "Primary Textbook",
    amazonUrl: "https://www.amazon.com/Object-Oriented-Analysis-Design-Applications-3rd/dp/020189551X/ref=sr_1_1?crid=2H3T5KI3Z9IY&dib=eyJ2IjoiMSJ9.83KIMOv3Cc7fNsb2DrYKHWITcJJXguFbejKHryUmxoJu2shw5sokosi6OFvOdbj4n8zZDrOL7-q_rjoUWrJOdLoIrY5p5DohxeKQR_WrZTb3ZdlagrDKD5JNwzM5nBQ-OGSc47AC9qSM16VCs6UZz6iq15kTAOj8sI13D_DlGBS2k3TBdVPn0ZDlSEm1qewk8jal6nHK90HnScT8RgSXceJlbFVrXE-REEobL91EvRU.ueRd2j_9H3TzHHmwETo_cgYJuJlDM74w7UMyysbt2Tg&dib_tag=se&keywords=Object-Oriented+Analysis+and+Design+with+Applications&qid=1779637815&s=books&sprefix=object-oriented+analysis+and+design+with+applications%2Cstripbooks%2C185&sr=1-1",
    color: "teal",
    chapters: [
      { num: 1,  title: "Complexity",                        pdf: "notes/ooad/ch01-complexity.pdf",         status: "wip" },
      { num: 2,  title: "The Object Model",                  pdf: "notes/ooad/ch02-object-model.pdf",       status: "planned" },
      { num: 3,  title: "Classes and Objects",               pdf: "notes/ooad/ch03-classes-objects.pdf",    status: "planned" },
      { num: 4,  title: "Classification",                    pdf: "notes/ooad/ch04-classification.pdf",    status: "planned" },
      { num: 5,  title: "Notation",                          pdf: "notes/ooad/ch05-notation.pdf",           status: "planned" },
      { num: 6,  title: "Process",                           pdf: "notes/ooad/ch06-process.pdf",            status: "planned" },
      { num: 7,  title: "Pragmatics",                        pdf: "notes/ooad/ch07-pragmatics.pdf",         status: "planned" },
      { num: 8,  title: "System Architecture: Satellite-Based Navigation", pdf: "notes/ooad/ch08-case-nav.pdf", status: "planned" },
      { num: 9,  title: "Control System: Traffic Management",pdf: "notes/ooad/ch09-case-traffic.pdf",      status: "planned" },
      { num: 10, title: "Artificial Intelligence: Cryptanalysis", pdf: "notes/ooad/ch10-case-crypto.pdf",  status: "planned" },
      { num: 11, title: "Data Acquisition: Weather Monitoring",   pdf: "notes/ooad/ch11-case-weather.pdf", status: "planned" },
      { num: 12, title: "Web Application: Homeowner's Association", pdf: "notes/ooad/ch12-case-web.pdf",   status: "planned" },
    ]
  },
  {
    id: "patterns",
    title: "Practical Design Patterns for Java Developers",
    edition: "1st Edition",
    authors: "Miroslav Wengner",
    year: 2023,
    publisher: "Packt Publishing",
    role: "Design Patterns Reference",
    amazonUrl: "#",
    color: "purple",
    chapters: [
      { num: 1,  title: "Introduction to Design Patterns",   pdf: "notes/design-patterns/ch01-intro.pdf",       status: "planned" },
      { num: 2,  title: "Creational Patterns",               pdf: "notes/design-patterns/ch02-creational.pdf",  status: "planned" },
      { num: 3,  title: "Structural Patterns",               pdf: "notes/design-patterns/ch03-structural.pdf",  status: "planned" },
      { num: 4,  title: "Behavioral Patterns",               pdf: "notes/design-patterns/ch04-behavioral.pdf",  status: "planned" },
      { num: 5,  title: "Concurrency Patterns",              pdf: "notes/design-patterns/ch05-concurrency.pdf", status: "planned" },
    ]
  }
];

// ── RENDER BOOKS ─────────────────────────────────────────────
function renderBooks() {
  const container = document.getElementById("booksContainer");
  container.innerHTML = books.map(book => `
    <div class="book-card book-${book.color}">

      <div class="book-header">
        <div class="book-meta-top">
          <span class="book-role-badge">${book.role}</span>
          <a class="book-amazon-link" href="${book.amazonUrl}" target="_blank" rel="noopener noreferrer"
             title="View on Amazon">
            View on Amazon ↗
          </a>
        </div>
        <h3 class="book-title">${book.title}</h3>
        <p class="book-edition">${book.edition} · ${book.publisher} · ${book.year}</p>
        <p class="book-authors">By ${book.authors}</p>
        <p class="book-credit-note">
          Notes below are my own chapter summaries. All intellectual property belongs to the authors and publisher.
        </p>
      </div>

      <div class="chapter-list">
        <div class="chapter-list-header">
          <span>Chapter</span>
          <span>Status</span>
        </div>
        ${book.chapters.map(ch => `
          <div class="chapter-row ${ch.status === 'planned' ? '' : 'chapter-available'}">
            <div class="chapter-info">
              <span class="chapter-num">Ch. ${String(ch.num).padStart(2,'0')}</span>
              <span class="chapter-title">${ch.title}</span>
            </div>
            <div class="chapter-actions">
              <span class="chapter-status status-${ch.status}">${statusLabel[ch.status]}</span>
              ${ch.status !== 'planned' ? `<a href="${ch.pdf}" target="_blank" class="chapter-pdf-link">PDF ↗</a>` : ''}
            </div>
          </div>
        `).join('')}
      </div>

      <div class="book-footer">
        <a href="${book.amazonUrl}" target="_blank" rel="noopener noreferrer" class="book-full-ref">
          <span class="ref-icon">📖</span>
          <span>${book.authors} (${book.year}). <em>${book.title}</em>. ${book.edition}. ${book.publisher}.</span>
          <span class="ref-link-label">Amazon ↗</span>
        </a>
      </div>

    </div>
  `).join('');
}

renderBooks();

// ── SCROLL FADE ───────────────────────────────────────────────
const observer = new IntersectionObserver((entries) => {
  entries.forEach(entry => {
    if (entry.isIntersecting) {
      entry.target.style.animationPlayState = "running";
      observer.unobserve(entry.target);
    }
  });
}, { threshold: 0.08 });

document.querySelectorAll("section.container").forEach(el => {
  el.style.animationPlayState = "paused";
  observer.observe(el);
});

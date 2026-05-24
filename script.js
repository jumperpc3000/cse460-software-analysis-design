const repoFiles = [
  {
    path: "README.md",
    desc: "Portfolio overview, skills demonstrated, and recruiter-facing summary",
    status: "wip"
  },
  {
    path: "project/README.md",
    desc: "Project overview, problem statement, and team information",
    status: "planned"
  },
  {
    path: "project/docs/",
    desc: "UML diagrams, design documents, and architecture specifications",
    status: "planned"
  },
  {
    path: "project/src/",
    desc: "Java source code and implementation files",
    status: "planned"
  },
  {
    path: "homeworks/hw1/",
    desc: "Homework 1 — OOP principles and object-oriented concepts",
    status: "planned"
  },
  {
    path: "homeworks/hw2/",
    desc: "Homework 2 — UML diagram specifications and modeling",
    status: "planned"
  },
  {
    path: "homeworks/hw3/",
    desc: "Homework 3 — Design pattern implementation in Java",
    status: "planned"
  },
  {
    path: "homeworks/hw4/",
    desc: "Homework 4 — Peer-reviewed assignment",
    status: "planned"
  },
  {
    path: "notes/design-patterns/",
    desc: "Notes and examples for Gang of Four design patterns",
    status: "planned"
  },
  {
    path: "notes/uml/",
    desc: "UML reference diagrams and modeling notes",
    status: "planned"
  },
  {
    path: "notes/architecture/",
    desc: "Software architectural styles comparison and documentation",
    status: "planned"
  }
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

// Fade-in on scroll for sections
const observer = new IntersectionObserver((entries) => {
  entries.forEach(entry => {
    if (entry.isIntersecting) {
      entry.target.style.animationPlayState = "running";
      observer.unobserve(entry.target);
    }
  });
}, { threshold: 0.1 });

document.querySelectorAll("section.container").forEach(el => {
  el.style.animationPlayState = "paused";
  observer.observe(el);
});

// ── REPO FILES ───────────────────────────────────────────────
const repoFiles = [
  { path: "README.md",                              desc: "Portfolio overview, skills demonstrated, and recruiter-facing summary",  status: "done" },
  { path: "Assignments/HW1/AlsulaimanF_HW1.pdf",   desc: "HW1 write-up — Astah setup, class diagrams, plugin review",              status: "done" },
  { path: "Assignments/HW1/Class_Diagram_Problem_1_2.md", desc: "Mermaid class diagram exported from Astah",                       status: "done" },
  { path: "Assignments/HW1/Exported Astah Java/",  desc: "Java source files exported from Astah (Course, Student, Person, etc.)",  status: "done" },
  { path: "Assignments/HW2/AlsulaimanF_HW2.pdf",    desc: "HW2 write-up — Heart Health Imaging System implementation and class diagram", status: "done" },
  { path: "Assignments/HW2/HeartHealthImagingSystem/", desc: "Java/JavaFX source — Patient Intake, CT Scan Tech, Patient View, Doctor View", status: "done" },
  { path: "Assignments/HW2/HW2 Implementation Class Diagram.png", desc: "Post-implementation Astah class diagram for the Heart Health system", status: "done" },
  { path: "Assignments/HW3/",                       desc: "Homework 3 — Design pattern implementation in Java",                     status: "planned" },
  { path: "Assignments/HW4/",                       desc: "Homework 4 — Peer-reviewed assignment",                                  status: "planned" },
  { path: "project/README.md",                      desc: "Project overview, problem statement, and team information",               status: "planned" },
  { path: "project/docs/",                          desc: "UML diagrams, design documents, and architecture specifications",         status: "planned" },
  { path: "project/src/",                           desc: "Java source code and implementation files",                               status: "planned" },
  { path: "notes/ooad/",                            desc: "OOAD textbook chapter notes (Booch et al.)",                             status: "wip" },
  { path: "notes/design-patterns/",                 desc: "Design patterns textbook notes (Wengner)",                               status: "planned" },
  { path: "notes/uml/",                             desc: "UML reference diagrams and modeling notes",                               status: "planned" },
  { path: "notes/architecture/",                    desc: "Software architectural styles comparison and documentation",              status: "planned" },
];

const statusLabel = { wip: "In Progress", done: "Complete", planned: "Planned" };

function renderList(items) {
  const list = document.getElementById("fileList");
  const noResults = document.getElementById("noResults");
  if (items.length === 0) { list.innerHTML = ""; noResults.style.display = "block"; return; }
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
  renderList(repoFiles.filter(f => f.path.toLowerCase().includes(q) || f.desc.toLowerCase().includes(q)));
});

// ── ASSIGNMENTS DATA ─────────────────────────────────────────
const assignments = [
  {
    id: "hw1",
    number: "HW1",
    title: "Exploring Astah and its Features",
    status: "done",
    skills: ["Astah", "UML Class Diagrams", "OOP Design", "Java Export", "Mermaid", "Plugin Usage"],
    summary: "Installed and configured Astah UML. Built UML class diagrams from scratch covering basic and advanced OOP relationships including inheritance, interface implementation, association, and composition. Exported diagrams to Java source code and Mermaid format using the Astah Mermaid Plugin.",
    problems: [
      { num: "P1", title: "Astah Setup & Basic Class Diagram", desc: "Created Student and Course classes with a many-to-many association in Astah." },
      { num: "P2", title: "Advanced Class Relationships",      desc: "Extended diagram with Person (abstract), Gradable (interface), and Department. Demonstrated inheritance, implementation, and composition. Exported to Java and Mermaid." },
      { num: "P3", title: "Astah Plugins",                    desc: "Installed and reviewed the Astah Mermaid Plugin. Exported class diagrams as Mermaid text and verified rendering in GitHub." },
    ],
    deliverables: [
      { label: "Write-up PDF",        icon: "📄", url: "Assignments/HW1/AlsulaimanF%23HW1.pdf",                                                                           type: "pdf" },
      { label: "Mermaid Diagram",     icon: "◈",  url: "https://github.com/jumperpc3000/cse460-software-analysis-design/blob/main/Assignments/HW1/Class%20Diagram%20Problem%201_2.md", type: "github" },
      { label: "Course.java",         icon: "☕", url: "https://github.com/jumperpc3000/cse460-software-analysis-design/blob/main/Assignments/HW1/Exported%20Astah%20java%20/Course.java",     type: "github" },
      { label: "Student.java",        icon: "☕", url: "https://github.com/jumperpc3000/cse460-software-analysis-design/blob/main/Assignments/HW1/Exported%20Astah%20java%20/Student.java",    type: "github" },
      { label: "Person.java",         icon: "☕", url: "https://github.com/jumperpc3000/cse460-software-analysis-design/blob/main/Assignments/HW1/Exported%20Astah%20java%20/Person.java",     type: "github" },
      { label: "Department.java",     icon: "☕", url: "https://github.com/jumperpc3000/cse460-software-analysis-design/blob/main/Assignments/HW1/Exported%20Astah%20java%20/Department.java", type: "github" },
      { label: "Gradable.java",       icon: "☕", url: "https://github.com/jumperpc3000/cse460-software-analysis-design/blob/main/Assignments/HW1/Exported%20Astah%20java%20/Gradable.java",   type: "github" },
    ]
  },
  {
    id: "hw2",
    number: "HW2",
    title: "Heart Health Imaging and Recording System",
    status: "done",
    skills: ["Java", "JavaFX", "GUI Design", "OOP", "UML Class Diagram", "Astah", "File I/O", "Role-Based Access"],
    summary: "Implemented the Heart Health Imaging and Recording System as a Java/JavaFX GUI application based on the Module 1 case study. Supports four user roles — Receptionist, CT Scan Technician, Patient, and Heart Specialist — with role-based access control, text-file persistence, and a full clinical workflow from patient intake through CT scan entry to risk assessment and patient communication.",
    problems: [
      { num: "Item 1", title: "Working Code",       desc: "Full JavaFX implementation covering Patient Intake, CT Scan Tech, Patient View, and Doctor View functionality with file-backed persistence." },
      { num: "Item 2", title: "Demo Video",          desc: "Video walkthrough showing compilation, all four functional areas, and live data written to text files." },
      { num: "Item 3", title: "Final Class Diagram", desc: "Post-implementation Astah class diagram reflecting all packages, classes, fields, methods, and relationships of the delivered system." },
    ],
    deliverables: [
      { label: "Write-up PDF",    icon: "📄", url: "Assignments/HW2/AlsulaimanF_HW2.pdf",                                                                                         type: "pdf"     },
      { label: "Source Code",     icon: "☕", url: "https://github.com/jumperpc3000/cse460-software-analysis-design/tree/main/Assignments/HW2/HeartHealthImagingSystem/src",                                                                                                  type: "github"  },
      { label: "Demo Video",      icon: "▶",  url: "https://asu.zoom.us/rec/share/48etXgGKyy4NSEb8YIXB7PvD_p1x8tp2v8Xlewhde41QS3pGlq2wn0oVtEJY1uYl.NTFs2VwHw2kWu_EX%20Passcode:%20m%5E2Wn1GG",                                                                                                   type: "github"  },
      { label: "Class Diagram",   icon: "◈",  url: "Assignments/HW2/HW2%20Implementation%20Class%20Diagram.png",                                                                    type: "diagram" },
    ]
  }
];

// ── RENDER ASSIGNMENTS ───────────────────────────────────────
function renderAssignments() {
  const container = document.getElementById("assignmentsContainer");
  container.innerHTML = assignments.map(hw => `
    <div class="hw-card" id="hw-${hw.id}">

      <div class="hw-header">
        <div class="hw-header-left">
          <span class="hw-number">${hw.number}</span>
          <div>
            <h3 class="hw-title">${hw.title}</h3>
            <p class="hw-summary">${hw.summary}</p>
          </div>
        </div>
        <div class="hw-header-right">
          <span class="hw-status status-${hw.status}">${statusLabel[hw.status]}</span>
          <button class="hw-toggle" onclick="toggleHW('${hw.id}')" aria-label="Toggle details">
            <span id="toggle-icon-${hw.id}">▼</span>
          </button>
        </div>
      </div>

      <div class="hw-tags">
        ${hw.skills.map(s => `<span class="tag">${s}</span>`).join('')}
      </div>

      <div class="hw-body" id="hw-body-${hw.id}">

        <!-- Problems -->
        <div class="hw-problems">
          <div class="hw-sub-label">Problems</div>
          ${hw.problems.map(p => `
            <div class="problem-row">
              <span class="problem-num">${p.num}</span>
              <div class="problem-info">
                <span class="problem-title">${p.title}</span>
                <span class="problem-desc">${p.desc}</span>
              </div>
            </div>
          `).join('')}
        </div>

        <!-- Deliverables -->
        <div class="hw-deliverables">
          <div class="hw-sub-label">Deliverables</div>
          <div class="deliverables-grid">
            ${hw.deliverables.map(d => d.type === 'diagram'
              ? `<button onclick="openDiagram('${d.url}')" class="deliverable-chip deliverable-diagram">
                   <span class="deliverable-icon">${d.icon}</span>
                   <span class="deliverable-label">${d.label}</span>
                   <span class="deliverable-arrow">⤢</span>
                 </button>`
              : `<a href="${d.url}" target="_blank" rel="noopener noreferrer" class="deliverable-chip deliverable-${d.type}">
                   <span class="deliverable-icon">${d.icon}</span>
                   <span class="deliverable-label">${d.label}</span>
                   <span class="deliverable-arrow">↗</span>
                 </a>`
            ).join('')}
          </div>
        </div>

      </div>
    </div>
  `).join('');

}

function toggleHW(id) {
  const body = document.getElementById(`hw-body-${id}`);
  const icon = document.getElementById(`toggle-icon-${id}`);
  const isOpen = body.classList.contains('open');
  body.classList.toggle('open', !isOpen);
  icon.textContent = isOpen ? '▼' : '▲';
}

// Open all assignments by default on load
window.addEventListener('load', () => {
  renderAssignments();
  setTimeout(() => {
    assignments.forEach(hw => {
      const body = document.getElementById(`hw-body-${hw.id}`);
      const icon = document.getElementById(`toggle-icon-${hw.id}`);
      if (body) { body.classList.add('open'); icon.textContent = '▲'; }
    });
  }, 100);
});

// ── BOOKS DATA ───────────────────────────────────────────────
const books = [
  {
    id: "ooad",
    title: "Object-Oriented Analysis and Design with Applications",
    edition: "3rd Edition",
    authors: "Grady Booch, Robert A. Maksimchuk, Michael W. Engle",
    year: 2007,
    publisher: "Addison-Wesley",
    role: "Primary Textbook",
    amazonUrl: "https://www.amazon.com/Object-Oriented-Analysis-Design-Applications-3rd/dp/020189551X/",
    color: "teal",
    chapters: [
      { num: 1,  title: "Complexity",                            pdf: "notes/ooad/ch01-complexity.pdf",      status: "planned" },
      { num: 2,  title: "The Object Model",                      pdf: "notes/ooad/ch02-object-model.pdf",    status: "planned" },
      { num: 3,  title: "Classes and Objects",                   pdf: "notes/ooad/ch03-classes-objects.pdf", status: "planned" },
      { num: 4,  title: "Classification",                        pdf: "notes/ooad/ch04-classification.pdf",  status: "planned" },
      { num: 5,  title: "Notation",                              pdf: "notes/ooad/ch05-notation.pdf",        status: "planned" },
      { num: 6,  title: "Process",                               pdf: "notes/ooad/ch06-process.pdf",         status: "planned" },
      { num: 7,  title: "Pragmatics",                            pdf: "notes/ooad/ch07-pragmatics.pdf",      status: "planned" },
      { num: 8,  title: "System Architecture: Satellite-Based Navigation", pdf: "notes/ooad/ch08-case-nav.pdf",     status: "planned" },
      { num: 9,  title: "Control System: Traffic Management",    pdf: "notes/ooad/ch09-case-traffic.pdf",    status: "planned" },
      { num: 10, title: "Artificial Intelligence: Cryptanalysis",pdf: "notes/ooad/ch10-case-crypto.pdf",     status: "planned" },
      { num: 11, title: "Data Acquisition: Weather Monitoring",  pdf: "notes/ooad/ch11-case-weather.pdf",    status: "planned" },
      { num: 12, title: "Web Application: Homeowner's Association", pdf: "notes/ooad/ch12-case-web.pdf",     status: "planned" },
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
      { num: 1, title: "Introduction to Design Patterns", pdf: "notes/design-patterns/ch01-intro.pdf",       status: "planned" },
      { num: 2, title: "Creational Patterns",             pdf: "notes/design-patterns/ch02-creational.pdf",  status: "planned" },
      { num: 3, title: "Structural Patterns",             pdf: "notes/design-patterns/ch03-structural.pdf",  status: "planned" },
      { num: 4, title: "Behavioral Patterns",             pdf: "notes/design-patterns/ch04-behavioral.pdf",  status: "planned" },
      { num: 5, title: "Concurrency Patterns",            pdf: "notes/design-patterns/ch05-concurrency.pdf", status: "planned" },
    ]
  }
];

function renderBooks() {
  const container = document.getElementById("booksContainer");
  container.innerHTML = books.map(book => `
    <div class="book-card book-${book.color}">
      <div class="book-header">
        <div class="book-meta-top">
          <span class="book-role-badge">${book.role}</span>
          <a class="book-amazon-link" href="${book.amazonUrl}" target="_blank" rel="noopener noreferrer">View on Amazon ↗</a>
        </div>
        <h3 class="book-title">${book.title}</h3>
        <p class="book-edition">${book.edition} · ${book.publisher} · ${book.year}</p>
        <p class="book-authors">By ${book.authors}</p>
        <p class="book-credit-note">Notes below are my own chapter summaries. All intellectual property belongs to the authors and publisher.</p>
      </div>
      <div class="chapter-list">
        <div class="chapter-list-header"><span>Chapter</span><span>Status</span></div>
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

// ── DIAGRAM LIGHTBOX ─────────────────────────────────────────
let _dScale = 1, _dTx = 0, _dTy = 0, _dDragging = false, _dLx = 0, _dLy = 0;

function openDiagram(src) {
  const modal = document.getElementById('diagramModal');
  const img   = document.getElementById('diagramImg');
  _dScale = 1; _dTx = 0; _dTy = 0;
  img.src = '';
  img.onload = () => _fitDiagram();
  img.src = src;
  modal.classList.add('open');
  document.body.style.overflow = 'hidden';
}

function closeDiagram() {
  document.getElementById('diagramModal').classList.remove('open');
  document.body.style.overflow = '';
}

function _fitDiagram() {
  const modal = document.getElementById('diagramModal');
  const img   = document.getElementById('diagramImg');
  _dScale = Math.min(modal.clientWidth / img.naturalWidth, modal.clientHeight / img.naturalHeight) * 0.94;
  _dTx = (modal.clientWidth  - img.naturalWidth  * _dScale) / 2;
  _dTy = (modal.clientHeight - img.naturalHeight * _dScale) / 2;
  _applyDiagram();
}

function _applyDiagram() {
  const img = document.getElementById('diagramImg');
  img.style.transform = `translate(${_dTx}px, ${_dTy}px) scale(${_dScale})`;
}

function _diagramZoom(delta, cx, cy) {
  const factor = delta > 0 ? 1.15 : 1 / 1.15;
  const next   = Math.min(Math.max(_dScale * factor, 0.1), 10);
  const ratio  = next / _dScale;
  _dTx = cx - ratio * (cx - _dTx);
  _dTy = cy - ratio * (cy - _dTy);
  _dScale = next;
  _applyDiagram();
}

window.addEventListener('load', () => {
  const inner = document.getElementById('diagramInner');
  if (!inner) return;

  // Scroll-to-zoom
  inner.addEventListener('wheel', e => {
    e.preventDefault();
    const r = inner.getBoundingClientRect();
    _diagramZoom(-e.deltaY, e.clientX - r.left, e.clientY - r.top);
  }, { passive: false });

  // Drag-to-pan
  inner.addEventListener('mousedown', e => {
    if (e.button !== 0) return;
    _dDragging = true; _dLx = e.clientX; _dLy = e.clientY;
    inner.classList.add('grabbing');
  });
  window.addEventListener('mousemove', e => {
    if (!_dDragging) return;
    _dTx += e.clientX - _dLx; _dTy += e.clientY - _dLy;
    _dLx = e.clientX; _dLy = e.clientY;
    _applyDiagram();
  });
  window.addEventListener('mouseup', () => {
    _dDragging = false;
    document.getElementById('diagramInner')?.classList.remove('grabbing');
  });

  // Close on backdrop click
  document.getElementById('diagramModal')?.addEventListener('click', e => {
    if (e.target === e.currentTarget) closeDiagram();
  });

  // Escape key
  document.addEventListener('keydown', e => {
    if (e.key === 'Escape') closeDiagram();
  });
});
